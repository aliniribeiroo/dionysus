package com.aliniribeiro.dionysus.controller.assets;

import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.model.assets.AssetRepository;
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
public class AssetsServiceTest {

    @Mock
    AssetRepository assetRepositoryMock;
    @Mock
    PersonRepository personRepositoryMock;
    @Mock
    MockServiceintegration mockServiceintegration;

    @InjectMocks
    private AssetsService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new AssetsService(assetRepositoryMock, personRepositoryMock, mockServiceintegration);
    }


    @Test
    public void saveAssets_shouldSavePersonAndAssets() {
        String originalId = UUID.randomUUID().toString();
        String type = "HOME_IMMOVABLE_PROPERTY";
        Double value = new Double(1235);
        String locale = "en_US";
        PersonEntity person = new PersonEntity();
        person.setCpf("123456");
        LocalDate lastUpdate = LocalDate.now();
        service.saveOrUpdatePersonAssets(originalId, type, value, locale, person, lastUpdate);
        Mockito.verify(assetRepositoryMock).save(Mockito.any());
        Mockito.verify(personRepositoryMock).save(Mockito.any());
    }
}
