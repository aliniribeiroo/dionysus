package com.aliniribeiro.mock.mockServices.controller.incomeandassets.contracts;

import com.aliniribeiro.mock.mockServices.controller.incomeandassets.enums.AssetsType;

import java.time.LocalDate;
import java.util.List;

public class IncomeAndAssetsOutput {

    public String id;
    public String cpf;
    public Integer birthYear;
    public String address;
    public LocalDate lastUpdate;
    public List<IncomeDTO> incomes;
    public List<AssetsType> assets;
}
