package com.aliniribeiro.dionysus.controller.income;


import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.model.income.IncomeEntity;
import com.aliniribeiro.dionysus.model.income.IncomeRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    PersonRepository personRepository;


    public void parsePersonIncomes(PersonEntity person, JSONArray debts, LocalDate lastUpdate) {
        debts.forEach(d -> {
            JSONObject dayInfo = (JSONObject) d;

            String originalId = dayInfo.get(StringConstants.ID) != null ? dayInfo.get(StringConstants.ID).toString() : null;
            String type = dayInfo.get(StringConstants.TYPE) != null ? dayInfo.get(StringConstants.TYPE).toString() : null;
            Double value = dayInfo.get(StringConstants.VALUE) != null ? new Double(dayInfo.get(StringConstants.VALUE).toString()) : null;
            String frequency = dayInfo.get(StringConstants.FREQUENCY) != null ? dayInfo.get(StringConstants.FREQUENCY).toString() : null;
            String locale = dayInfo.get(StringConstants.LOCALE) != null ? dayInfo.get(StringConstants.LOCALE).toString() : null;
            saveOrUpdatePersonIncomes(originalId, type, value, frequency, locale, person, lastUpdate);
        });
    }

    private void saveOrUpdatePersonIncomes(String originalId, String type, Double value, String frequency, String locale, PersonEntity person, LocalDate lastUpdate) {
        IncomeEntity income = incomeRepository.findByOriginalId(originalId);
        if (income == null) {
            income = new IncomeEntity();
            income.setOriginalId(originalId);
            income.setPersonId(person.getCpf());
        }
        income.setLocale(locale);
        income.setFrequency(frequency);
        income.setIncomeValue(value);
        income.setLastUpdate(lastUpdate);
        income.setType(type);
        incomeRepository.save(income);

        person.setLastIncomeUpdate(LocalDate.now());
        personRepository.save(person);
    }

}
