package com.aliniribeiro.dionysus.controller.debts;

import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import com.aliniribeiro.dionysus.controller.debts.mapper.DebtsMapper;
import com.aliniribeiro.dionysus.model.common.PageResult;
import com.aliniribeiro.dionysus.model.debt.DebtEntity;
import com.aliniribeiro.dionysus.model.debt.DebtRepository;
import com.aliniribeiro.dionysus.model.person.PersonEntity;
import com.aliniribeiro.dionysus.model.person.PersonRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class DebtService {

    @Autowired
    DebtRepository debtRepository;

    @Autowired
    DebtsMapper mapper;


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

            String locale = dayInfo.get(StringConstants.LOCALE) != null ? dayInfo.get(StringConstants.LOCALE).toString() : null;
            String originalId = dayInfo.get(StringConstants.ID) != null ? dayInfo.get(StringConstants.ID).toString() : null;
            LocalDate lastUpdate = dayInfo.get(StringConstants.LASTUPDATE) != null ? LocalDate.parse(dayInfo.get(StringConstants.LASTUPDATE).toString()) : null;
            LocalDate originDate = dayInfo.get(StringConstants.ORIGINDATE) != null ? LocalDate.parse(dayInfo.get(StringConstants.ORIGINDATE).toString()) : null;
            String description = dayInfo.get(StringConstants.DESCRIPTION) != null ? dayInfo.get(StringConstants.DESCRIPTION).toString() : null;
            String status = dayInfo.get(StringConstants.STATUS) != null ? dayInfo.get(StringConstants.STATUS).toString() : null;
            Double value = dayInfo.get(StringConstants.VALUE) != null ? new Double(dayInfo.get(StringConstants.VALUE).toString()) : null;
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
     * @return GetDebtsOutput com as informações das dívidas encontradas.
     */
    public GetDebtsOutput getDebts(String cpf, Long page, Long size) {
        PageResult<DebtEntity> debts = debtRepository.getDebts(cpf, page, size);
        GetDebtsOutput output = new GetDebtsOutput();
        output.registerFound = debts.getTotalCount();
        output.debts = new ArrayList<>();
        debts.getRows().stream().forEach(c -> output.debts.add(mapper.toDebtsDTO(c)));
        return output;
    }
}
