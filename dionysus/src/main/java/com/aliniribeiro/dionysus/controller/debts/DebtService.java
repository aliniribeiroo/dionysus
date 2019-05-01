package com.aliniribeiro.dionysus.controller.debts;

import com.aliniribeiro.dionysus.common.StringConstants;
import com.aliniribeiro.dionysus.controller.debts.mapper.DebtsMapper;
import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import com.aliniribeiro.dionysus.common.util.JsonParserHelper;
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
    DebtsMapper mapper;

    @Autowired
    MockServiceintegration mockServiceintegration;

    @Autowired
    PersonRepository personRepository;

    /**
     * Serviço que faz o parse das informaçoes de dívidas da pessoa, dados recebidos do serviço A.
     *
     * @param person Pessoa
     * @param debts  JSONArray com uma lista de dívidas da pessoa.
     */
    public void parsePersonDebts(PersonEntity person, JSONArray debts) {
        debts.forEach(d -> {
            JSONObject dayInfo = (JSONObject) d;

            String originalId = JsonParserHelper.toString(dayInfo, StringConstants.ID);
            String locale = JsonParserHelper.toString(dayInfo, StringConstants.LOCALE);
            LocalDate lastUpdate = JsonParserHelper.toLocalDate(dayInfo, StringConstants.LASTUPDATE);
            LocalDate originDate = JsonParserHelper.toLocalDate(dayInfo, StringConstants.ORIGINDATE);
            String description = JsonParserHelper.toString(dayInfo, StringConstants.DESCRIPTION);
            String status = JsonParserHelper.toString(dayInfo, StringConstants.STATUS);
            Double value = JsonParserHelper.toDouble(dayInfo, StringConstants.VALUE);
            saveOrUpdatePersonDebts(originalId, locale, lastUpdate, originDate, description, status, value, person);
        });
    }

    /**
     * Método que salva ou atualiza as informações de uma dívida.
     *
     * @param originalId  Id de origem da divida no serviço A.
     * @param locale      Localização da pessoa, para converter o valor da moeda corretamente.
     * @param lastUpdate  Data da última atualização desta informação.
     * @param originDate  Data que a divida se originou.
     * @param description Descrição da dívida.
     * @param status      Situação da dívida.
     * @param value       valor da dívida.
     * @param person      Pessoa.
     */
    protected void saveOrUpdatePersonDebts(String originalId, String locale, LocalDate lastUpdate, LocalDate originDate, String description, String status, Double value, PersonEntity person) {
        DebtEntity debt = debtRepository.getByOriginalId(originalId);
        if (debt == null) {
            debt = new DebtEntity();
            debt.setOriginalId(originalId);
            debt.setPersonId(person.getCpf());
            debt.setOriginalDate(originDate);
        }

        debt.setLocale(locale);
        debt.setLastUpdate(lastUpdate);
        debt.setDescription(description);
        debt.setStatus(status);
        debt.setDebtValue(value);
        debt.setPersonId(person.getCpf());
        debtRepository.save(debt);

        person.setLastDebtsUpdate(LocalDate.now());
        personRepository.save(person);
    }


    /**
     * Método que retorna todas as dívidas do CPF.
     *
     * @param cpf  CPF que a dívida será solicitada.
     * @param page pagina a ser encontrada.
     * @param size tamanho da página a ser encontrada.
     * @return PageResult com as informações das dívidas encontradas.
     */
    public PageResult getDebts(String cpf, Long page, Long size){
        return debtRepository.getDebts(cpf, page, size);
    }

}
