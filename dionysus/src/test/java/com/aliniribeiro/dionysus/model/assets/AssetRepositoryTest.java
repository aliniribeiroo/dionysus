package com.aliniribeiro.dionysus.model.assets;

import com.aliniribeiro.dionysus.model.person.PersonEntity;
import javafx.application.Application;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AssetRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AssetRepository assetRepository;

    @Test
    public void getByOriginalId() {
        PersonEntity person = new PersonEntity();
        person.setCpf("12345");
        person.setAddress("xanana");
        person.setLastAdrdessUpdate(LocalDate.now());
        entityManager.persistAndFlush(person);

        String originId1 = UUID.randomUUID().toString();
        AssetEntity asset1 = new AssetEntity();
        asset1.setPersonId("12345");
        asset1.setOriginalId(originId1);
        asset1.setType("APPARTMENT_IMMOVABLE_PROPERTY");
        asset1.setLocale("en_US");
        asset1.setAssetsValue(new Double(1235));
        asset1 = entityManager.persistAndFlush(asset1);

        String originId2 = UUID.randomUUID().toString();
        AssetEntity asset2 = new AssetEntity();
        asset2.setPersonId("12345");
        asset2.setOriginalId(originId2);
        asset2.setType("APPARTMENT_IMMOVABLE_PROPERTY");
        asset2.setLocale("en_US");
        asset2.setAssetsValue(new Double(1235));
        entityManager.persistAndFlush(asset2);

        AssetEntity output = assetRepository.getByOriginalId(originId1);
        Assertions.assertThat(output).isEqualTo(asset1);
    }
}
