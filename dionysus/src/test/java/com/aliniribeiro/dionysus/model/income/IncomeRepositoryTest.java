package com.aliniribeiro.dionysus.model.income;

import com.aliniribeiro.dionysus.model.common.PageResult;
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
public class IncomeRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IncomeRepository incomeRepository;

    @After
    public void cleanUp() {
        incomeRepository.deleteAll();
    }

    @Test
    public void getByOriginalId() {
        PersonEntity person = new PersonEntity();
        person.setCpf("12345");
        person.setAddress("xanana");
        person.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person);

        String originId1 = UUID.randomUUID().toString();
        IncomeEntity income1 = new IncomeEntity();
        income1.setPersonId("12345");
        income1.setOriginalId(originId1);
        income1.setFrequency("MOUNTHLY");
        income1.setType("REGULAR_WORK");
        income1.setIncomeValue(new Double(12345));
        income1.setLocale("en_US");
        income1 = entityManager.persistAndFlush(income1);

        String originId2 = UUID.randomUUID().toString();
        IncomeEntity income2 = new IncomeEntity();
        income2.setPersonId("12345");
        income2.setOriginalId(originId2);
        income2.setFrequency("MOUNTHLY");
        income2.setType("REGULAR_WORK");
        income2.setIncomeValue(new Double(12345));
        income2.setLocale("en_US");
        entityManager.persistAndFlush(income2);

        IncomeEntity output = incomeRepository.getByOriginalId(originId1);
        Assertions.assertThat(output).isEqualTo(income1);
    }

    @Test
    public void getAssets_shouldReturn_One() {
        PersonEntity person = new PersonEntity();
        person.setCpf("12345");
        person.setAddress("xanana");
        person.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person);

        String originId1 = UUID.randomUUID().toString();
        IncomeEntity income1 = new IncomeEntity();
        income1.setPersonId("12345");
        income1.setOriginalId(originId1);
        income1.setFrequency("MOUNTHLY");
        income1.setType("REGULAR_WORK");
        income1.setLocale("en_US");
        income1.setIncomeValue(new Double(1235));
        income1 = entityManager.persistAndFlush(income1);

        String originId2 = UUID.randomUUID().toString();
        IncomeEntity income2 = new IncomeEntity();
        income2.setPersonId("12345");
        income2.setOriginalId(originId2);
        income2.setFrequency("MOUNTHLY");
        income2.setType("REGULAR_WORK");
        income2.setIncomeValue(new Double(12345));
        income2.setLocale("en_US");
        income2 = entityManager.persistAndFlush(income2);

        String cpf = person.getCpf();
        Long page = 1L;
        Long size = 1L;
        PageResult output = incomeRepository.getIncomes(cpf, page, size);

        Assertions.assertThat(output.getTotalCount()).isEqualTo(2L);
        Assertions.assertThat(output.getRows().size()).isEqualTo(1L);
    }
    
}
