package com.aliniribeiro.dionysus.controller.debts;

import com.aliniribeiro.dionysus.common.Response;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import com.aliniribeiro.dionysus.controller.person.PersonService;
import com.aliniribeiro.dionysus.controller.searchhistory.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebtController {

    @Autowired
    PersonService personService;

    @Autowired
    SearchHistoryService searchHistoryService;

    @PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('USER')")
    @GetMapping(value = "/dionysus/getDebts/{cpf}/{page}/{size}")
    public ResponseEntity<Response<GetDebtsOutput>> getDebts(@PathVariable("cpf") String cpf, @PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<GetDebtsOutput> response = new Response<GetDebtsOutput>();
        try {
            GetDebtsOutput dataControl = personService.getPersonDebts(cpf, page, size);
            if (dataControl != null){
                response.setData(dataControl);
                searchHistoryService.saveSearchHistory(cpf);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();

            }
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
