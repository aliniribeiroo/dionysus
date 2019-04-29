package com.aliniribeiro.dionysus.controller.assets;

import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import com.aliniribeiro.dionysus.model.assets.AssetRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AssetsService {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    PersonRepository personRepository;

    public void parsePersonAssetss(PersonEntity person, JSONArray debts, LocalDate lastUpdate) {
        debts.forEach(d -> {
            JSONObject dayInfo = (JSONObject) d;

            String originalId = dayInfo.get(StringConstants.ID) != null ? dayInfo.get(StringConstants.ID).toString() : null;
            String type = dayInfo.get(StringConstants.TYPE) != null ? dayInfo.get(StringConstants.TYPE).toString() : null;
            Double value = dayInfo.get(StringConstants.VALUE) != null ? new Double(dayInfo.get(StringConstants.VALUE).toString()) : null;
            String locale = dayInfo.get(StringConstants.LOCALE) != null ? dayInfo.get(StringConstants.LOCALE).toString() : null;
            saveOrUpdatePersonAssets(originalId, type, value, locale, person, lastUpdate);
        });
    }

    private void saveOrUpdatePersonAssets(String originalId, String type, Double value, String locale, PersonEntity person, LocalDate lastUpdate) {
        AssetEntity assets = assetRepository.findByOriginalId(originalId);
        if (assets == null){
            assets = new AssetEntity();
            assets.setOriginalId(originalId);
            assets.setPersonId(person.getCpf());
        }
        assets.setLocale(locale);
        assets.setLastUpdate(lastUpdate);
        assets.setType(type);
        assets.setAssetsValue(value);
        assetRepository.save(assets);

        person.setLastAssetsUpdate(LocalDate.now());
        personRepository.save(person);
    }
    
}
