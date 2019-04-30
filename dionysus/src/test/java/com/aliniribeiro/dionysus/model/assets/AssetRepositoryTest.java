package com.aliniribeiro.dionysus.model.assets;

import com.aliniribeiro.dionysus.model.common.RepositoryBaseTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class AssetRepositoryTest  extends RepositoryBaseTest {

//    @Autowired
//    AssetRepository assetRepository;
//
//    @Test
//    public void getByOriginalId(){
//        String originId1 = UUID.randomUUID().toString();
//        AssetEntity asset1 = new AssetEntity();
//        asset1.setPersonCPF("12345");
//        asset1.setOriginalId(originId1);
//        assetRepository.save(asset1);
//
//        String originId2= UUID.randomUUID().toString();
//        AssetEntity asset2 = new AssetEntity();
//        asset2.setPersonCPF("1234554");
//        asset2.setOriginalId(originId2);
//        assetRepository.save(asset2);
//
//        AssetEntity output = assetRepository.getByOriginalId(originId1);
//        Assertions.assertThat(output).isEqualTo(asset1);
//    }
}
