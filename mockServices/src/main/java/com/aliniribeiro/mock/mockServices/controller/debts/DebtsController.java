package com.aliniribeiro.mock.mockServices.controller.debts;

import com.aliniribeiro.mock.mockServices.controller.common.Response;
import com.aliniribeiro.mock.mockServices.controller.debts.contracts.DebtsInput;
import com.aliniribeiro.mock.mockServices.controller.debts.contracts.DebtsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DebtsController {

    @Autowired
    private DebtsService debtsService;


    /**
     * API que retorna todas as dividas da base de dados
     * @return
     */
    @PostMapping(value = "/mockA/getAllCPFDebts")
    public ResponseEntity<Response<List<DebtsOutput>>> getAllCPFDebts(@RequestBody DebtsInput input) {
        Response<List<DebtsOutput>> response = new Response<List<DebtsOutput>>();
        try {
            List<DebtsOutput> pastHosted = debtsService.getAlLDebts(input);
            response.setData(pastHosted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * API que retorna todas as dividas da base de dados
     * @return
     */
    @GetMapping(value = "/mockA/getAllCPFDebts/{cpf}")
    public ResponseEntity<Response<DebtsOutput>> getAllCPFDebts(@PathVariable String cpf) {
        Response<DebtsOutput> response = new Response<DebtsOutput>();
        try {
            DebtsOutput pastHosted = debtsService.getCPFDebts(cpf);
            response.setData(pastHosted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }



}
