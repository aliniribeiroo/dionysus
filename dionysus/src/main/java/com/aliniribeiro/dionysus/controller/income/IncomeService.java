package com.aliniribeiro.dionysus.controller.income;


import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.model.income.IncomeEntity;
import com.aliniribeiro.dionysus.model.income.IncomeRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import com.aliniribeiro.dionysus.util.JsonParserHelper;
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

            String originalId = JsonParserHelper.toString(dayInfo, StringConstants.ID);
            String locale = JsonParserHelper.toString(dayInfo, StringConstants.LOCALE);
            Double value = JsonParserHelper.toDouble(dayInfo, StringConstants.VALUE);
            String type = JsonParserHelper.toString(dayInfo, StringConstants.TYPE);
            String frequency = JsonParserHelper.toString(dayInfo, StringConstants.FREQUENCY);
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
