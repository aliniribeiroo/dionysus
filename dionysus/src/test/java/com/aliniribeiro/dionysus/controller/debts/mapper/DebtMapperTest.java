package com.aliniribeiro.dionysus.controller.debts.mapper;

import com.aliniribeiro.dionysus.controller.debts.contracts.DebtsDTO;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DebtMapperTest {

    @InjectMocks
    DebtsMapper mapper;


    @Test
    public void toGetDebtsOutput(){
        DebtEntity debtEntity = new DebtEntity();
        debtEntity.setDebtValue(new Double(12345));
        debtEntity.setDescription("Description");
        debtEntity.setOriginalDate(LocalDate.now());
        debtEntity.setLastUpdate(LocalDate.now());
        debtEntity.setOriginalId(UUID.randomUUID().toString());
        debtEntity.setLocale("en_US");
        debtEntity.setPersonId("1234564");
        debtEntity.setStatus("OPPEN");
        DebtsDTO output =  mapper.toDebtsDTO(debtEntity);

        Assertions.assertThat(output.value).isEqualTo(debtEntity.getDebtValue());
        Assertions.assertThat(output.description).isEqualTo(debtEntity.getDescription());
        Assertions.assertThat(output.originDate).isEqualTo(debtEntity.getOriginalDate());
        Assertions.assertThat(output.lastUpdate).isEqualTo(debtEntity.getLastUpdate());
        Assertions.assertThat(output.locale).isEqualTo(debtEntity.getLocale());
        Assertions.assertThat(output.status).isEqualTo(debtEntity.getStatus());
    }

}
