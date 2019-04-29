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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

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

                PersonEntity person = savePerson(cpf, name, address);
                debtService.parsePersonDebts(person, ((JSONArray) dayInfo.get(StringConstants.DEBTS)));
            });
            dataControlService.updateData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
                PersonEntity person = updatePerson(cpf, birthYear, address, lastUpdate);
                incomeService.parsePersonIncomes(person, (JSONArray) dayInfo.get(StringConstants.INCOMES), lastUpdate);
                assetsService.parsePersonAssetss(person, (JSONArray) dayInfo.get(StringConstants.ASSETS), lastUpdate);
            });
            dataControlService.updateData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private PersonEntity savePerson(String cpf, String name, String address) {
        LocalDate now = LocalDate.now();
        PersonEntity person = new PersonEntity();
        person.setCpf(cpf);
        person.setAddress(address);
        person.setLastAdrdessUpdate(now);
        person.setName(name);
        return personRepository.save(person);
    }

    private PersonEntity updatePerson(String cpf, Integer birthYear, String address, LocalDate lastUpdate) {
        PersonEntity personEntity = personRepository.findBycpf(cpf);
        personEntity.setBirthYear(birthYear);
        if (!personEntity.getAddress().equalsIgnoreCase(address) &&
                personEntity.getLastAdrdessUpdate().isAfter(lastUpdate)){
            personEntity.setAddress(address);
            personEntity.setLastAdrdessUpdate(lastUpdate);
        }
        return personRepository.save(personEntity);
    }
}
