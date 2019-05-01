package com.aliniribeiro.dionysus.controller.person;

import com.aliniribeiro.dionysus.controller.common.Response;
import com.aliniribeiro.dionysus.controller.person.contracts.GetCPFPointsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {


    @Autowired
    PersonService personService;

    @GetMapping(value = "/dionysus/CPFPoints/{cpf}")
    public ResponseEntity<Response<GetCPFPointsOutput>> getCPFPoints(@PathVariable("cpf") String cpf) {
        Response<GetCPFPointsOutput> response = new Response<GetCPFPointsOutput>();
        try {
            GetCPFPointsOutput output = personService.getCPFPoints(cpf);
            response.setData(output);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
