package com.aliniribeiro.dionysus.controller.income;


import com.aliniribeiro.dionysus.controller.assets.AssetsService;
import com.aliniribeiro.dionysus.model.assets.AssetRepository;
import com.aliniribeiro.dionysus.model.income.IncomeRepository;
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
public class IncomeServiceTest {

    @Mock
    IncomeRepository incomeRepository;
    @Mock
    PersonRepository personRepositoryMock;

    @InjectMocks
    private IncomeService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void saveAssets_shouldSavePersonAndAssets() {
        String originalId = UUID.randomUUID().toString();
        String type = "REGULAR_WORK";
        String frequency = "MOUNTHLY";
        Double value = new Double(1235);
        String locale = "en_US";
        PersonEntity person = new PersonEntity();
        person.setCpf("123456");
        LocalDate lastUpdate = LocalDate.now();
        service.saveOrUpdatePersonIncomes(originalId, type, value, frequency, locale, person, lastUpdate);
        Mockito.verify(incomeRepository).save(Mockito.any());
        Mockito.verify(personRepositoryMock).save(Mockito.any());
    }

}
