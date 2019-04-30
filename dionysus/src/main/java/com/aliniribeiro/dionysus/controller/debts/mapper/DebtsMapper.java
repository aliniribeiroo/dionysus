package com.aliniribeiro.dionysus.controller.debts.mapper;

import com.aliniribeiro.dionysus.controller.debts.contracts.DebtsDTO;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import org.springframework.stereotype.Component;

@Component
public class DebtsMapper {

    /**
     * Mapeia a entidade DebtEntity para um GetDebtsOutput
     * @param debtEntity entidade que será mapeada.
     * @return Objeto DebtsDTO com os dados da entidade.
     */
    public DebtsDTO toDebtsDTO(DebtEntity debtEntity) {
        DebtsDTO debt = new DebtsDTO();
        debt.originDate = debtEntity.getOriginalDate();
        debt.lastUpdate = debtEntity.getLastUpdate();
        debt.description = debtEntity.getDescription();
        debt.status = debtEntity.getStatus();
        debt.locale = debtEntity.getLocale();
        debt.value = debtEntity.getDebtValue();
        return debt;
    }
}
