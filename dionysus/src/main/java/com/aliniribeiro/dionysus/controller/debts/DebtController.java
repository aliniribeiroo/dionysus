package com.aliniribeiro.dionysus.controller.debts;

import com.aliniribeiro.dionysus.controller.common.Response;
import com.aliniribeiro.dionysus.controller.debts.contracts.GetDebtsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebtController {

    @Autowired
    DebtService debtService;

    @GetMapping(value = "/diunysus/getDebts/{cpf}/{page}/{size}")
    public ResponseEntity<Response<GetDebtsOutput>> getDebts(@PathVariable("cpf") String cpf, @PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<GetDebtsOutput> response = new Response<GetDebtsOutput>();
        try {
            GetDebtsOutput dataControl = debtService.getDebts(cpf, page, size);
            if (dataControl != null){
                response.setData(dataControl);
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
