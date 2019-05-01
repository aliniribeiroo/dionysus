package com.aliniribeiro.dionysus.controller.events.mapper;

import com.aliniribeiro.dionysus.controller.events.contracts.CredictCardPurchaseDTO;
import com.aliniribeiro.dionysus.controller.events.contracts.EstablishmentDTO;
import com.aliniribeiro.dionysus.controller.events.contracts.FinancialMovementDTO;
import com.aliniribeiro.dionysus.controller.events.contracts.LastSearchDTO;
import org.springframework.stereotype.Component;

@Component
public class EventsMapper {

    public CredictCardPurchaseDTO toCredictCardPurchaseDTO(Double value, String locale, String date, String establishmentCity, String establishmentName) {
        CredictCardPurchaseDTO dto = new CredictCardPurchaseDTO();
        dto.value = value;
        dto.locale = locale;
        dto.date = date;
        dto.establishment = new EstablishmentDTO();
        dto.establishment.city = establishmentCity;
        dto.establishment.name = establishmentName;
        return dto;
    }

    public FinancialMovementDTO toFinancialMovementDTO(Double value, String locale, String date, String description) {
        FinancialMovementDTO dto = new FinancialMovementDTO();
        dto.date = date;
        dto.description = description;
        dto.locale = locale;
        dto.value = value;
        return dto;
    }

    public LastSearchDTO toLastSearchDTO(String date,  String establishmentCity, String establishmentName) {
        LastSearchDTO dto = new LastSearchDTO();
        dto.date = date;
        dto.establishment = new EstablishmentDTO();
        dto.establishment.city = establishmentCity;
        dto.establishment.name = establishmentName;
        return dto;
    }
}
