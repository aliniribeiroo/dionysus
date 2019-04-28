package com.aliniribeiro.mock.mockServices.controller.events;

import com.aliniribeiro.mock.mockServices.controller.common.MockUtils;
import com.aliniribeiro.mock.mockServices.controller.events.contracts.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CPFEventsService {

    protected EventsOutput getEventsOutput(String cpf) {
        EventsOutput output = new EventsOutput();
        output.financialMovement = getFinancialMovement(cpf);
        output.lastCredictCardPurchase = getLastCredictCardPurchase(cpf);
        output.lastSearch = getLastSearch();
        return output;
    }

    private List<FinancialMovementDTO> getFinancialMovement(String cpf) {
        List<FinancialMovementDTO> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FinancialMovementDTO dto = new FinancialMovementDTO();
            dto.id = MockUtils.getRandomId();
            dto.date = MockUtils.getRandomDate();
            dto.description = "Alterou valores de purpurina do banco c para o banco d";
            dto.locale = Locale.getDefault();
            dto.value = new Float(25441.25);
            list.add(dto);
        }
        return list;
    }

    private List<CredictCardPurchaseDTO> getLastCredictCardPurchase(String cpf) {
        List<CredictCardPurchaseDTO> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CredictCardPurchaseDTO dto = new CredictCardPurchaseDTO();
            dto.id = MockUtils.getRandomId();
            dto.date = MockUtils.getRandomDate();
            dto.locale = Locale.getDefault();
            dto.value = new Float(25441.25);
            dto.establishment = getEstablishment();
            list.add(dto);
        }
        return list;
    }

    private EstablishmentDTO getEstablishment() {
        EstablishmentDTO dto = new EstablishmentDTO();
        dto.id = MockUtils.getRandomId();
        dto.city = "São Paulo";
        dto.name = "Xananana";
        return dto;
    }

    private LastSearchDTO getLastSearch() {
        LastSearchDTO dto = new LastSearchDTO();
        dto.id = MockUtils.getRandomId();
        dto.establishment = getEstablishment();
        dto.date = MockUtils.getRandomDate();
        return dto;
    }
}
