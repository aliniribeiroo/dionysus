package com.aliniribeiro.dionysus.controller.income.mapper;

import com.aliniribeiro.dionysus.controller.income.contracts.IncomeDTO;
import com.aliniribeiro.dionysus.model.income.IncomeEntity;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    /**
     * Mapeia a entidade IncomeEntity para um GetDebtsOutput
     * @param incomeEntity entidade que será mapeada.
     * @return Objeto IncomeDTO com os dados da entidade.
     */
    public IncomeDTO toIncomeDTO(IncomeEntity incomeEntity) {
        IncomeDTO income = new IncomeDTO();
        income.frequency = incomeEntity.getFrequency();
        income.type = incomeEntity.getType();
        income.value = incomeEntity.getIncomeValue();
        income.locale = incomeEntity.getLocale();

        return income;
    }
}
