package com.aliniribeiro.dionysus.controller.person;


import com.aliniribeiro.dionysus.controller.assets.AssetsService;
import com.aliniribeiro.dionysus.controller.datacontrol.DataControlService;
import com.aliniribeiro.dionysus.controller.debts.DebtService;
import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.controller.income.IncomeService;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
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
    DataControlService dataControlService;


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

                String cpf = dayInfo.get(StringConstants.CPF) != null ? dayInfo.get(StringConstants.CPF).toString() : null;
                String address = dayInfo.get(StringConstants.ADDRESS) != null ? dayInfo.get(StringConstants.ADDRESS).toString() : null;
                String name = dayInfo.get(StringConstants.NAME) != null ? dayInfo.get(StringConstants.NAME).toString() : null;

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

                String cpf = dayInfo.get(StringConstants.CPF) != null ? dayInfo.get(StringConstants.CPF).toString() : null;
                Integer birthYear = dayInfo.get(StringConstants.BIRTH_YEAR) != null ? Integer.parseInt(dayInfo.get(StringConstants.BIRTH_YEAR).toString()) : null;
                String address = dayInfo.get(StringConstants.ADDRESS) != null ? dayInfo.get(StringConstants.ADDRESS).toString() : null;
                LocalDate lastUpdate = dayInfo.get(StringConstants.LASTUPDATE) != null ? LocalDate.parse(dayInfo.get(StringConstants.LASTUPDATE).toString()) : null;
                PersonEntity person = saveOrUpdatePerson(cpf, Optional.empty(), address, Optional.of(birthYear), lastUpdate);
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
        PersonEntity person = personRepository.getByCPF(cpf);
        if (person == null) {
            person = new PersonEntity();
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

}
