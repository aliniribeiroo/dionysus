package com.aliniribeiro.mock.mockServices.controller.events.contracts;

import java.time.LocalDate;
import java.util.Locale;

public class CredictCardPurchaseDTO {

    public String id;
    public EstablishmentDTO establishment;
    public LocalDate date;
    public float value;
    public Locale locale;
}
