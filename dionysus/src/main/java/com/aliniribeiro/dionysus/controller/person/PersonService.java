package com.aliniribeiro.dionysus.controller.person;


import com.aliniribeiro.dionysus.controller.assets.AssetsService;
import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.controller.datacontrol.DataControlService;
import com.aliniribeiro.dionysus.controller.debts.DebtService;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import com.aliniribeiro.dionysus.controller.income.IncomeService;
import com.aliniribeiro.dionysus.controller.income.contracts.PersonIncomeOutput;
import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.controller.assets.contracts.PersonAssetsOutput;
import com.aliniribeiro.dionysus.controller.person.mapper.PersonMapper;
import com.aliniribeiro.dionysus.model.assets.AssetEntity;
import com.aliniribeiro.dionysus.model.assets.AssetRepository;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepository;
import com.aliniribeiro.dionysus.model.income.IncomeEntity;
import com.aliniribeiro.dionysus.model.income.IncomeRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import com.aliniribeiro.dionysus.util.JsonParserHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    DebtService debtService;

    @Autowired
    IncomeService incomeService;

    @Autowired
    AssetsService assetsService;

    @Autowired
    MockServiceintegration mockServiceintegration;

    @Autowired
    DataControlService dataControlService;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    PersonMapper personMapper;

    @Autowired
    DebtRepository debtRepository;

    /**
     * Método que carrega as informações do Serviço A.
     */
    public void loadServiceAPersonData() {
        Optional<DataControlEntity> dataControl = dataControlService.getDataControl();
        Optional<String> jsonStr = mockServiceintegration.loadServiceAData(dataControl);
        parsePersonInformation(jsonStr.get());
    }

    /**
     * Método que carrega as informações do Serviço B.
     */
    public void loadServicBPersonData() {
        Optional<DataControlEntity> dataControl = dataControlService.getDataControl();
        Optional<String> jsonStr = mockServiceintegration.loadServiceBData(dataControl);
        parsePersonIncomeAndAssets(jsonStr.get());
    }

    /**
     * Método que realiza o Parse das informações recebidas do serviço A.
     *
     * @param jsonStr Json com os dados recebidos.
     */
    public void parsePersonInformation(String jsonStr) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);
            JSONArray data = (JSONArray) jsonObject.get(StringConstants.DATA);
            data.forEach(d -> {
                JSONObject dayInfo = (JSONObject) d;

                String cpf = JsonParserHelper.toString(dayInfo, StringConstants.CPF);
                String address = JsonParserHelper.toString(dayInfo, StringConstants.ADDRESS);
                String name = JsonParserHelper.toString(dayInfo, StringConstants.FULL_NAME);
                PersonEntity person = saveOrUpdatePerson(cpf, Optional.of(name), address, Optional.empty(), LocalDate.now());
                debtService.parsePersonDebts(person, ((JSONArray) dayInfo.get(StringConstants.DEBTS)));
            });
            dataControlService.updateData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que realiza o Parse das informações recebidas do serviço B.
     *
     * @param jsonStr Json com os dados recebidos.
     */
    public void parsePersonIncomeAndAssets(String jsonStr) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);
            JSONArray data = (JSONArray) jsonObject.get(StringConstants.DATA);
            data.forEach(d -> {
                JSONObject dayInfo = (JSONObject) d;

                String cpf = JsonParserHelper.toString(dayInfo, StringConstants.CPF);
                String address = JsonParserHelper.toString(dayInfo, StringConstants.ADDRESS);
                Integer birthYear = JsonParserHelper.toInteger(dayInfo, StringConstants.ID);
                LocalDate lastUpdate = JsonParserHelper.toLocalDate(dayInfo, StringConstants.LASTUPDATE);
                String name = JsonParserHelper.toString(dayInfo, StringConstants.FULL_NAME);

                PersonEntity person = saveOrUpdatePerson(cpf, Optional.of(name), address, Optional.of(birthYear), lastUpdate);
                incomeService.parsePersonIncomes(person, (JSONArray) dayInfo.get(StringConstants.INCOMES), lastUpdate);
                assetsService.parsePersonAssetss(person, (JSONArray) dayInfo.get(StringConstants.ASSETS), lastUpdate);
            });
            dataControlService.updateData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Método que cria e/ou atualiza uma pessoa na base de dados.
     *
     * @param cpf        CPF da pessoa.
     * @param name       Nome da pessoa.
     * @param address    Endereço da pessoa.
     * @param birthYear  Ano de nascimento da pessoa.
     * @param lastUpdate data da última atualização deste registro.
     * @return Pessoa criada/ou alterada.
     */
    private PersonEntity saveOrUpdatePerson(String cpf, Optional<String> name, String address, Optional<Integer> birthYear, LocalDate lastUpdate) {
        Optional<PersonEntity> personOptional = personRepository.getByCPF(cpf);
        PersonEntity person = personOptional.isPresent() ? personOptional.get() : new PersonEntity();
        if (person.getCpf() == null) {
            person.setCpf(cpf);
            person.setAddress(address);
            person.setLastAdrdessUpdate(lastUpdate);
        }
        if (!person.getAddress().equalsIgnoreCase(address) &&
                person.getLastAdrdessUpdate().isAfter(lastUpdate)) {
            person.setAddress(address);
            person.setLastAdrdessUpdate(lastUpdate);
        }
        if (birthYear.isPresent()) {
            person.setBirthYear(birthYear.get());
        }

        if (name.isPresent()) {
            person.setName(name.get());
        }

        return personRepository.save(person);
    }

    /**
     * Método que retorna todos os bens do CPF.
     *
     * @param cpf  CPF que possui os bens.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return PersonAssetsOutput com as informações dos bens encontradas.
     */
    public PersonAssetsOutput getCPFAssetsList(String cpf, Long page, Long size) {
        Optional<PersonEntity> personEntity = personRepository.getByCPF(cpf);
        if (!personEntity.isPresent()) {
            Optional<String> jsonStr = mockServiceintegration.getAssetsAndIncomeByCPF(cpf);
            personEntity = parsePersonIncomeAndAssetsByCPF(jsonStr.get());
        }
        PageResult<AssetEntity> assets = assetRepository.getAssets(cpf, page, size);
        return personMapper.toPersonAssetsOutput(assets, personEntity.get().getName());
    }

    /**
     * Método que retorna todos das rendas do CPF.
     *
     * @param cpf  CPF que possui o rendimento.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return PersonIncomeOutput com as informações dos rendimentos encontrados.
     */

    public PersonIncomeOutput getCPFIncomeList(String cpf, Long page, Long size) {
        Optional<PersonEntity> personEntity = personRepository.getByCPF(cpf);
        if (!personEntity.isPresent()) {
            Optional<String> jsonStr = mockServiceintegration.getAssetsAndIncomeByCPF(cpf);
            personEntity = parsePersonIncomeAndAssetsByCPF(jsonStr.get());
        }

        PageResult<IncomeEntity> incomes = incomeRepository.getIncomes(cpf, page, size);
        return personMapper.toPersonIncomeOutput(incomes, personEntity.get().getName());
    }

    /**
     * Método que realiza o Parse das informações recebidas do serviço B.
     *
     * @param jsonStr Json com os dados recebidos.
     */
    public Optional<PersonEntity> parsePersonIncomeAndAssetsByCPF(String jsonStr) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);
            JSONObject data = (JSONObject) jsonObject.get(StringConstants.DATA);

            String cpf = JsonParserHelper.toString(data, StringConstants.CPF);
            String address = JsonParserHelper.toString(data, StringConstants.ADDRESS);
            String name = JsonParserHelper.toString(data, StringConstants.FULL_NAME);
            Integer birthYear = JsonParserHelper.toInteger(data, StringConstants.ID);
            LocalDate lastUpdate = JsonParserHelper.toLocalDate(data, StringConstants.LASTUPDATE);

            PersonEntity person = saveOrUpdatePerson(cpf, Optional.of(name), address, Optional.of(birthYear), lastUpdate);
            incomeService.parsePersonIncomes(person, (JSONArray) data.get(StringConstants.INCOMES), lastUpdate);
            assetsService.parsePersonAssetss(person, (JSONArray) data.get(StringConstants.ASSETS), lastUpdate);
            return Optional.ofNullable(person);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Método que retorna todas as dívidas do CPF.
     *
     * @param cpf  CPF que a dívida será solicitada.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return GetDebtsOutput com as informações das dívidas encontradas.
     */
    public GetDebtsOutput getPersonDebts(String cpf, Long page, Long size) {
        Optional<PersonEntity> personEntity = personRepository.getByCPF(cpf);
        if (!personEntity.isPresent()) {
            Optional<String> jsonStr = mockServiceintegration.getAssetsAndIncomeByCPF(cpf);
            personEntity = parsePersonIncomeAndAssetsByCPF(jsonStr.get());
        }
        PageResult<DebtEntity> debts = debtRepository.getDebts(cpf, page, size);
        return personMapper.toGetDebtsOutput(debts, personEntity.get().getName(), personEntity.get().getCpf());
    }
}
