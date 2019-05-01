package com.aliniribeiro.dionysus.controller.assets;

import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import com.aliniribeiro.dionysus.model.assets.AssetRepository;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import com.aliniribeiro.dionysus.util.JsonParserHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AssetsService {



    AssetRepository assetRepository;
    PersonRepository personRepository;
    MockServiceintegration mockServiceintegration;

    @Autowired
    public AssetsService(AssetRepository assetRepository, PersonRepository personRepository,   MockServiceintegration mockServiceintegration) {
        this.assetRepository = assetRepository;
        this.personRepository = personRepository;
        this.mockServiceintegration = mockServiceintegration;
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

            String originalId = JsonParserHelper.toString(dayInfo, StringConstants.ID);
            String type = JsonParserHelper.toString(dayInfo, StringConstants.TYPE);
            Double value = JsonParserHelper.toDouble(dayInfo, StringConstants.VALUE);
            String locale = JsonParserHelper.toString(dayInfo, StringConstants.LOCALE);
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


    /**
     * Método que retorna todos os bens do CPF.
     *
     * @param cpf  CPF que a dívida será solicitada.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return PageResult com as informações das dívidas encontradas.
     */
    public PageResult getAssets(String cpf, Long page, Long size){
        return assetRepository.getAssets(cpf, page, size);
    }


    /**
     * Método que retorna todos os bens de uma pessoa.
     * @param cpf CPF da pessoa.
     * @return Lista de Bens de uma pessoa.
     */
    public List<AssetEntity> getAllAssets(String cpf){
        return assetRepository.getAllAssets(cpf);
    }

}
