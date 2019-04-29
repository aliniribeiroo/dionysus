package com.aliniribeiro.dionysus.controller.person;

import com.aliniribeiro.dionysus.controller.common.Response;
import com.aliniribeiro.dionysus.controller.datacontrol.DataControlService;
import com.aliniribeiro.dionysus.controller.mockserviceintegration.MockServiceintegration;
import com.aliniribeiro.dionysus.model.datacontrol.DataControlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private MockServiceintegration mockServiceintegration;


    @Autowired
    DataControlService dataControlService;

    @GetMapping(value = "/diunysus/firstLoadData")
    public ResponseEntity<Response<Boolean>> firstLoad() {
        Response<Boolean> response = new Response<Boolean>();
        try {
            Optional<DataControlEntity> dataControl = dataControlService.getDataControl();
            mockServiceintegration.loadServiceAData(dataControl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
