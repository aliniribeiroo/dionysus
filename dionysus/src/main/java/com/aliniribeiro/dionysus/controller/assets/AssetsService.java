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

    AssetRepository assetRepository;
    PersonRepository personRepository;

    @Autowired
    public AssetsService(AssetRepository assetRepository, PersonRepository personRepository) {
        this.assetRepository = assetRepository;
        this.personRepository = personRepository;
    }

    /**
     * Método que faz o parse das informações do Json de retorno do serviço A.
     *
     * @param person     Pessoa.
     * @param debts      dívidas da pessoa.
     * @param lastUpdate data da última atualização desta informação.
     */
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

    /**
     * Método salva ou atualiza as informações dos bens da pessoa.
     *
     * @param originalId Id de origem do serviço B.
     * @param type       tipo de bem que a pessoa possui.
     * @param value      valor do bem.
     * @param locale     Localização da pessoa, para converter o valor da moeda corretamente.
     * @param person     Pessoa.
     * @param lastUpdate Data da última atualização desta informação.
     */
    protected void saveOrUpdatePersonAssets(String originalId, String type, Double value, String locale, PersonEntity person, LocalDate lastUpdate) {
        AssetEntity assets = assetRepository.getByOriginalId(originalId);
        if (assets == null) {
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
