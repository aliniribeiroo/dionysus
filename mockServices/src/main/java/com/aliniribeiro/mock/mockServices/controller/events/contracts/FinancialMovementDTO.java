package com.aliniribeiro.mock.mockServices.controller.events.contracts;

import java.time.LocalDate;
import java.util.Locale;

public class FinancialMovementDTO {

    public String id;
    public String description;
    public LocalDate date;
    public float value;
    public Locale locale;
}
