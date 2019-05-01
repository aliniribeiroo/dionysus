package com.aliniribeiro.dionysus.controller.income.contracts;

import com.aliniribeiro.dionysus.controller.debts.contracts.DebtsDTO;

import java.util.List;

public class PersonIncomeOutput {

    public String personName;
    public List<IncomeDTO> incomes;
    public Long registerFound;
}
