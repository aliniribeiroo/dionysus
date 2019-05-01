package com.aliniribeiro.dionysus.model.debts;

import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DebtRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DebtRepository debtRepository;

    @After
    public void cleanUp() {
        debtRepository.deleteAll();
    }

    @Test
    public void getByOriginalId() {
        PersonEntity person = new PersonEntity();
        person.setCpf("12345");
        person.setAddress("xanana");
        person.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person);

        String originId1 = UUID.randomUUID().toString();
        DebtEntity debts1 = new DebtEntity();
        debts1.setPersonId("12345");
        debts1.setOriginalId(originId1);
        debts1.setOriginalDate(LocalDate.now());
        debts1.setDescription("Description");
        debts1.setStatus("OPPEN");
        debts1.setDebtValue(new Double(12345));
        debts1.setLocale("en_US");
        debts1 = entityManager.persistAndFlush(debts1);

        String originId2 = UUID.randomUUID().toString();
        DebtEntity debts2 = new DebtEntity();
        debts2.setPersonId("12345");
        debts2.setOriginalId(originId2);
        debts2.setOriginalDate(LocalDate.now());
        debts2.setDescription("Description");
        debts2.setStatus("OPPEN");
        debts2.setDebtValue(new Double(12345));
        debts2.setLocale("en_US");
        entityManager.persistAndFlush(debts2);

        DebtEntity output = debtRepository.getByOriginalId(originId1);
        Assertions.assertThat(output).isEqualTo(debts1);
    }

    @Test
    public void getAssets_shouldReturn_One() {
        PersonEntity person = new PersonEntity();
        person.setCpf("12345");
        person.setAddress("xanana");
        person.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person);

        String originId1 = UUID.randomUUID().toString();
        DebtEntity debts1 = new DebtEntity();
        debts1.setPersonId("12345");
        debts1.setOriginalDate(LocalDate.now());
        debts1.setOriginalId(originId1);
        debts1.setDescription("Description");
        debts1.setStatus("OPPEN");
        debts1.setLocale("en_US");
        debts1.setDebtValue(new Double(1235));
        debts1 = entityManager.persistAndFlush(debts1);

        String originId2 = UUID.randomUUID().toString();
        DebtEntity debts2 = new DebtEntity();
        debts2.setPersonId("12345");
        debts2.setOriginalId(originId2);
        debts2.setOriginalDate(LocalDate.now());
        debts2.setDescription("Description");
        debts2.setStatus("OPPEN");
        debts2.setDebtValue(new Double(12345));
        debts2.setLocale("en_US");
        debts2 = entityManager.persistAndFlush(debts2);

        String cpf = person.getCpf();
        Long page = 1L;
        Long size = 1L;
        PageResult output = debtRepository.getDebts(cpf, page, size);

        Assertions.assertThat(output.getTotalCount()).isEqualTo(2L);
        Assertions.assertThat(output.getRows().size()).isEqualTo(1L);
    }
}
