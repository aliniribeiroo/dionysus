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

@Service
public class IncomeService {

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    PersonRepository personRepository;


    /**
     * Método que faz o parse das informações do Json de retorno do serviço B.
     *
     * @param person     pessoa que recebe a renda.
     * @param incomes    renda da pessoa.
     * @param lastUpdate data da última atualização desta informação.
     */
    public void parsePersonIncomes(PersonEntity person, JSONArray incomes, LocalDate lastUpdate) {
        incomes.forEach(d -> {
            JSONObject dayInfo = (JSONObject) d;

            String originalId = dayInfo.get(StringConstants.ID) != null ? dayInfo.get(StringConstants.ID).toString() : null;
            String type = dayInfo.get(StringConstants.TYPE) != null ? dayInfo.get(StringConstants.TYPE).toString() : null;
            Double value = dayInfo.get(StringConstants.VALUE) != null ? new Double(dayInfo.get(StringConstants.VALUE).toString()) : null;
            String frequency = dayInfo.get(StringConstants.FREQUENCY) != null ? dayInfo.get(StringConstants.FREQUENCY).toString() : null;
            String locale = dayInfo.get(StringConstants.LOCALE) != null ? dayInfo.get(StringConstants.LOCALE).toString() : null;
            saveOrUpdatePersonIncomes(originalId, type, value, frequency, locale, person, lastUpdate);
        });
    }

    /**
     * Método salva ou atualiza as informações dos bens da pessoa.
     *
     * @param originalId Id de origem do serviço B.
     * @param type       tipo de renda que a pessoa recebe.
     * @param value      valor da renda.
     * @param frequency  frequência que ela recebe esta renda.
     * @param locale     Localização da pessoa, para converter o valor da moeda corretamente.
     * @param person     Pessoa.
     * @param lastUpdate Data da última atualização desta informação.
     */
    protected void saveOrUpdatePersonIncomes(String originalId, String type, Double value, String frequency, String locale, PersonEntity person, LocalDate lastUpdate) {
        IncomeEntity income = incomeRepository.getByOriginalId(originalId);
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
