package com.aliniribeiro.dionysus.controller.searchhistory;

import com.aliniribeiro.dionysus.common.Response;
import com.aliniribeiro.dionysus.controller.person.contracts.GetCPFPointsOutput;
import com.aliniribeiro.dionysus.controller.searchhistory.contracts.SearchHistoryOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchHistoryController {

    @Autowired
    SearchHistoryService service;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/dionysus/searchHistory/{cpf}")
    public ResponseEntity<Response<List<SearchHistoryOutput>>> searchHistory(@PathVariable("cpf") String cpf) {
        Response<List<SearchHistoryOutput>> response = new Response<List<SearchHistoryOutput>>();
        try {
            List<SearchHistoryOutput> output = service.searchHistory(cpf);
            response.setData(output);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
