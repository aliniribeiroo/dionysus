package com.aliniribeiro.mock.mockServices.controller.incomeandassets.contracts;

import com.aliniribeiro.mock.mockServices.controller.incomeandassets.enums.IncomeFrequency;
import com.aliniribeiro.mock.mockServices.controller.incomeandassets.enums.IncomeType;

import java.util.Locale;

public class IncomeDTO {

    public String id;
    public IncomeType type;
    public float value;
    public IncomeFrequency frequency;
    public Locale locale;
}
