package com.aliniribeiro.dionysus.controller.income;

import com.aliniribeiro.dionysus.controller.common.Response;
import com.aliniribeiro.dionysus.controller.income.contracts.PersonIncomeOutput;
import com.aliniribeiro.dionysus.controller.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncomeController {

    @Autowired
    PersonService personService;

    @GetMapping(value = "/dionysus/getCPFIncome/{cpf}/{page}/{size}")
    public ResponseEntity<Response<PersonIncomeOutput>> getLastSearch(@PathVariable String cpf,  @PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<PersonIncomeOutput> response = new Response<PersonIncomeOutput>();
        try {
            PersonIncomeOutput output = personService.getCPFIncomeList(cpf, page, size);
            response.setData(output);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
