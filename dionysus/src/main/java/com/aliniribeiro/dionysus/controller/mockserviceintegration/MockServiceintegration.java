package com.aliniribeiro.dionysus.controller.mockserviceintegration;

import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class MockServiceintegration {


    private final static Logger LOGGER = Logger.getLogger(MockServiceintegration.class.getName());

    /**
     * Método que faz a carga dos dados do Serviço A.
     * Se a primeira carga já foi realizada uma vez, a busca é feita apenas dos dados do dia anterior.
     */
    public Optional<String> loadServiceAData(Optional<DataControlEntity> dataControl) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String URI = StringConstants.ALL_DEBTS_URL;
            if (dataControl.get().getFirstLoaded()) {
                LocalDate lastUpdatedData = dataControl.get().getLastUpdate();
                LocalDate now = LocalDate.now();
                URI = String.format(StringConstants.DATE_DEBTS_URL, lastUpdatedData.toString(), now);
            }
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            });
            return Optional.ofNullable(responseEntity.getBody());

        } catch (Exception e) {
            LOGGER.severe("Error gettting A service data!");
        }
        return Optional.empty();
    }


    /**
     * Método que faz a carga dos dados do Serviço B.
     * Se a primeira carga já foi realizada uma vez, a busca é feita apenas dos dados do dia anterior.
     */
    public Optional<String> loadServiceBData(Optional<DataControlEntity> dataControl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URI = StringConstants.ALL_INCOME_AND_ASSETS_URL;
            if (dataControl.get().getFirstLoaded()) {
                LocalDate lastUpdatedData = dataControl.get().getLastUpdate();
                LocalDate now = LocalDate.now();
                URI = String.format(StringConstants.DATE_INCOME_AND_ASSETS_URL, lastUpdatedData.toString(), now);
            }
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            });
            return Optional.ofNullable(responseEntity.getBody());
        } catch (Exception e) {
            LOGGER.severe("Error gettting B service data!");
        }
        return Optional.empty();
    }

    public Optional<String> getServiceCLastCPFSearch(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URI = String.format(StringConstants.LAST_SEARCH_URL, cpf);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            });
            String response = responseEntity.getBody();
            return Optional.ofNullable(response);

        } catch (Exception e) {
            LOGGER.severe("Error gettting B service data!");
        }

        return Optional.empty();
    }

    public Optional<String> getFinancialMovement(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URI = String.format(StringConstants.FINANCIAL_MOVEMENT_URL, cpf);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            });
            String response = responseEntity.getBody();
            return Optional.ofNullable(response);

        } catch (Exception e) {
            LOGGER.severe("Error gettting B service data!");
        }

        return Optional.empty();
    }

    public Optional<String> getLastCredictCardPurchase(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URI = String.format(StringConstants.LAST_CREDICT_CARD_PURCHASE_URL, cpf);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            });
            String response = responseEntity.getBody();
            return Optional.ofNullable(response);

        } catch (Exception e) {
            LOGGER.severe("Error gettting B service data!");
        }

        return Optional.empty();
    }


    public Optional<String> getAssetsAndIncomeByCPF(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URI = String.format(StringConstants.CPF_INCOME_AND_ASSETS_URL, cpf);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            });
            return Optional.ofNullable(responseEntity.getBody());

        } catch (Exception e) {
            LOGGER.severe("Error gettting B service data!");
        }
        return Optional.empty();
    }


}
