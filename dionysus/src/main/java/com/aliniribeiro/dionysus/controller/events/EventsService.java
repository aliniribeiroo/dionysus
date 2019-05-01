package com.aliniribeiro.dionysus.controller.events;

import com.aliniribeiro.dionysus.common.StringConstants;
import com.aliniribeiro.dionysus.controller.events.contracts.CredictCardPurchaseDTO;
import com.aliniribeiro.dionysus.controller.events.contracts.FinancialMovementDTO;
import com.aliniribeiro.dionysus.controller.events.contracts.LastSearchDTO;
import com.aliniribeiro.dionysus.controller.events.mapper.EventsMapper;
import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.common.util.JsonParserHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EventsService {

    @Autowired
    MockServiceintegration mockServiceintegration;

    @Autowired
    EventsMapper mapper;

    public List<FinancialMovementDTO> getFinancialMovement(String cpf) {
        Optional<String> jsonStr = mockServiceintegration.getFinancialMovement(cpf);
        List<FinancialMovementDTO> movements = jsonStr.isPresent() ? parseFinancialMovements(jsonStr.get()) : Collections.emptyList();
        return movements;
    }

    public CredictCardPurchaseDTO getLastCredictCardPurchase(String cpf) {
        Optional<String> jsonStr = mockServiceintegration.getLastCredictCardPurchase(cpf);
        Optional<CredictCardPurchaseDTO> lastPutchase = jsonStr.isPresent() ? parseLastCredictCardPurchase(jsonStr.get()) : Optional.empty();
        return lastPutchase.get();
    }


    public LastSearchDTO getLastSearch(String cpf) {
        Optional<String> jsonStr = mockServiceintegration.getServiceCLastCPFSearch(cpf);
        Optional<LastSearchDTO> lastSearchDTO = jsonStr.isPresent() ? parseLastCPFSerarch(jsonStr.get()) : Optional.empty();
        return lastSearchDTO.get();
    }

    /**
     * Método que faz o parse dos dados da String de Json retornada da API e retorna um LastSearchDTO.
     * @param jsonStr String que será realizada o parse.
     * @return LastSearchDTO com os dados das última busca pelo CPF.
     */
    public Optional<LastSearchDTO> parseLastCPFSerarch(String jsonStr) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);
            JSONObject data = (JSONObject) jsonObject.get(StringConstants.DATA);
            String date = JsonParserHelper.toString(data, StringConstants.DATE);
            JSONObject establishment = (JSONObject) data.get(StringConstants.ESTABLISHMENT);
            String establishmentName = JsonParserHelper.toString(establishment, StringConstants.NAME);
            String establishmentCity = JsonParserHelper.toString(establishment, StringConstants.CITY);

            LastSearchDTO dto = mapper.toLastSearchDTO(date, establishmentCity, establishmentName);
            return Optional.ofNullable(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }


    /**
     * Método que faz o parse dos dados da String de Json retornada da API e retorna um FinancialMovementDTO.
     * @param jsonStr String que será realizada o parse.
     * @return FinancialMovementDTO com os dados das últimas movimentações do CPF.
     */
    public List<FinancialMovementDTO> parseFinancialMovements(String jsonStr) {
        List<FinancialMovementDTO> movementDTOList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);
            JSONArray data = (JSONArray) jsonObject.get(StringConstants.DATA);
            data.forEach(d -> {
                JSONObject dataObject = (JSONObject) d;
                String description = JsonParserHelper.toString(dataObject, StringConstants.DESCRIPTION);
                String date = JsonParserHelper.toString(dataObject, StringConstants.DATE);
                Double value = JsonParserHelper.toDouble(dataObject, StringConstants.VALUE);
                String locale = JsonParserHelper.toString(dataObject, StringConstants.LOCALE);

                FinancialMovementDTO dto = mapper.toFinancialMovementDTO(value, locale, date, description);
                movementDTOList.add(dto);
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return movementDTOList;
    }

    /**
     * Método que faz o parse dos dados da String de Json retornada da API e retorna um CredictCardPurchaseDTO.
     * @param jsonStr String que será realizada o parse.
     * @return CredictCardPurchaseDTO com os dados da compra de cartão de crédito.
     */
    public Optional<CredictCardPurchaseDTO> parseLastCredictCardPurchase(String jsonStr) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);
            JSONObject data = (JSONObject) jsonObject.get(StringConstants.DATA);
            String date = JsonParserHelper.toString(data, StringConstants.DATE);
            Double value = JsonParserHelper.toDouble(data, StringConstants.VALUE);
            String locale = JsonParserHelper.toString(data, StringConstants.LOCALE);
            JSONObject establishment = (JSONObject) data.get(StringConstants.ESTABLISHMENT);
            String establishmentName = JsonParserHelper.toString(establishment, StringConstants.NAME);
            String establishmentCity = JsonParserHelper.toString(establishment, StringConstants.CITY);

            CredictCardPurchaseDTO dto = mapper.toCredictCardPurchaseDTO(value, locale, date, establishmentCity, establishmentName);
            return Optional.ofNullable(dto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

}
