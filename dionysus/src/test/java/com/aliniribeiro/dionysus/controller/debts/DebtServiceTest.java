package com.aliniribeiro.dionysus.controller.debts;

import com.aliniribeiro.dionysus.model.debt.DebtRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DebtServiceTest {


    @Mock
    DebtRepository debtRepository;
    @Mock
    PersonRepository personRepositoryMock;

    @InjectMocks
    private DebtService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void saveAssets_shouldSavePersonAndAssets() {
        String originalId = UUID.randomUUID().toString();
        String description = "Description";
        String status = "OPPEN";
        Double value = new Double(1235);
        String locale = "en_US";
        PersonEntity person = new PersonEntity();
        person.setCpf("123456");
        LocalDate lastUpdate = LocalDate.now();
        LocalDate originDate = LocalDate.now();
        service.saveOrUpdatePersonDebts(originalId, locale, lastUpdate, originDate, description, status, value, person);
        Mockito.verify(debtRepository).save(Mockito.any());
        Mockito.verify(personRepositoryMock).save(Mockito.any());
    }
}
