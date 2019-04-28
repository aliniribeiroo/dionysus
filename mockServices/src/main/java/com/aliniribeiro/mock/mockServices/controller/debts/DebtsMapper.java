package com.aliniribeiro.mock.mockServices.controller.debts;

import com.aliniribeiro.mock.mockServices.controller.common.MockUtils;
import com.aliniribeiro.mock.mockServices.controller.debts.contracts.DebtDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DebtsMapper {

    public List<DebtDTO> getNormalDebts() {
        List<DebtDTO> debts = new ArrayList<>();
        for (int i = 0; i < 25; i ++ ){
            DebtDTO debt = new DebtDTO();
            debt.id = MockUtils.getRandomId();
            debt.originDate = LocalDate.now().minusYears(2);
            debt.lastUpdate = LocalDate.now().minusMonths(10);
            debt.description = "Pink dye hair";
            debts.add(debt);
        }
        return debts;
    }

    public List<DebtDTO> getExtraDataDebts() {
        List<DebtDTO> debts = new ArrayList<>();
        for (int i = 0; i < 100; i ++ ){
            DebtDTO debt = new DebtDTO();
            debt.id = MockUtils.getRandomId();
            debt.originDate = LocalDate.now().minusYears(2);
            debt.lastUpdate = LocalDate.now().minusMonths(10);
            debt.description = "Pink dye hair";
            debts.add(debt);
        }
        return debts;
    }

    public List<DebtDTO> getDebtsByDate(LocalDate startDate, LocalDate endDate) {
        List<DebtDTO> debts = new ArrayList<>();
        for (int i = 0; i < 100; i ++ ){
            DebtDTO debt = new DebtDTO();
            debt.id = MockUtils.getRandomId();
            debt.originDate = startDate;
            debt.lastUpdate = endDate;
            debt.description = "Pink dye hair";
            debts.add(debt);
        }
        return debts;
    }
}
