package com.aliniribeiro.dionysus.controller.debts;

import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DebtService {

    @Autowired
    DebtRepository debtRepository;

    @Autowired
    PersonRepository personRepository;


    public void parsePersonDebts(PersonEntity person, JSONArray debts) {
        debts.forEach(d -> {
            JSONObject dayInfo = (JSONObject) d;

            String locale = dayInfo.get(StringConstants.LOCALE) != null ? dayInfo.get(StringConstants.LOCALE).toString() : null;
            String id = dayInfo.get(StringConstants.ID) != null ? dayInfo.get(StringConstants.ID).toString() : null;
            LocalDate lastUpdate = dayInfo.get(StringConstants.LASTUPDATE) != null ? LocalDate.parse(dayInfo.get(StringConstants.LASTUPDATE).toString()) : null;
            LocalDate originDate = dayInfo.get(StringConstants.ORIGINDATE) != null ? LocalDate.parse(dayInfo.get(StringConstants.ORIGINDATE).toString()) : null;
            String description = dayInfo.get(StringConstants.DESCRIPTION) != null ? dayInfo.get(StringConstants.DESCRIPTION).toString() : null;
            String status = dayInfo.get(StringConstants.STATUS) != null ? dayInfo.get(StringConstants.STATUS).toString() : null;
            Double value = dayInfo.get(StringConstants.VALUE) != null ? new Double(dayInfo.get(StringConstants.VALUE).toString()) : null;
            savePersonDebts(id, locale, lastUpdate, originDate, description, status, value, person);
        });
    }

    private void savePersonDebts(String id, String locale, LocalDate lastUpdate, LocalDate originDate, String description, String status, Double value, PersonEntity person) {
        DebtEntity debt = new DebtEntity();
        debt.setLocale(locale);
        debt.setOriginalId(id);
        debt.setLastUpdate(lastUpdate);
        debt.setOriginalDate(originDate);
        debt.setDescription(description);
        debt.setStatus(status);
        debt.setDebtValue(value);
        debt.setPersonId(person.getCpf());
        debtRepository.save(debt);

        person.setLastDebtsUpdate(LocalDate.now());
        personRepository.save(person);
    }
}
