package com.aliniribeiro.dionysus.controller.mockserviceintegration;

import com.aliniribeiro.dionysus.controller.common.StringConstants;
import com.aliniribeiro.dionysus.controller.datacontrol.DataControlService;
import com.aliniribeiro.dionysus.controller.person.PersonService;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class MockServiceintegration {

    @Autowired
    PersonService personService;

    @Autowired
    DataControlService dataControlService;

    public void loadData(){
        System.out.println("Scheduler foi chamado");
        Optional<DataControlEntity> dataControl = dataControlService.getDataControl();
        loadServiceAData(dataControl);
        loadServiceBData(dataControl);
    }

    /**
     * Método que faz a carga dos dados do Serviço A.
     * Se a primeira carga já foi realizada uma vez, a busca é feita apenas dos dados do dia anterior.
     */
    public void loadServiceAData(Optional<DataControlEntity> dataControl) {
        RestTemplate restTemplate = new RestTemplate();

        String URI = StringConstants.ALL_DEBTS_URL;
        if (dataControl.get().getFirstLoaded()){
            LocalDate lastUpdatedData = dataControl.get().getLastUpdate();
            LocalDate now = LocalDate.now();
            URI = String.format(StringConstants.DATE_DEBTS_URL, lastUpdatedData.toString(), now);
        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        });
        String response = responseEntity.getBody();
        personService.parsePersonInformation(response);
    }


    /**
     * Método que faz a carga dos dados do Serviço B.
     * Se a primeira carga já foi realizada uma vez, a busca é feita apenas dos dados do dia anterior.
     */
    public void loadServiceBData(Optional<DataControlEntity> dataControl) {
        RestTemplate restTemplate = new RestTemplate();

        String URI = StringConstants.ALL_INCOME_AND_ASSETS_URL;
        if (dataControl.get().getFirstLoaded()){
            LocalDate lastUpdatedData = dataControl.get().getLastUpdate();
            LocalDate now = LocalDate.now();
            URI = String.format(StringConstants.DATE_INCOME_AND_ASSETS_URL, lastUpdatedData.toString(), now);
        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        });
        String response = responseEntity.getBody();
        personService.parsePersonIncomeAndAssets(response);
    }
}
