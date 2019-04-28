package com.aliniribeiro.mock.mockServices.controller.incomeandassets;

import com.aliniribeiro.mock.mockServices.controller.common.Response;
import com.aliniribeiro.mock.mockServices.controller.debts.contracts.DebtsOutput;
import com.aliniribeiro.mock.mockServices.controller.incomeandassets.contracts.IncomeAndAssetsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncomeAndAssetsController {

    @Autowired
    private IncomeAndAssetsService incomeService;


    /**
     * API que retorna todas as dividas da base de dados
     * @return
     */
    @GetMapping(value = "/mockB/getCPFIncomeAndAssets/{cpf}")
    public ResponseEntity<Response<IncomeAndAssetsOutput>> getIncomeAndAssets(@PathVariable String cpf) {
        Response<IncomeAndAssetsOutput> response = new Response<IncomeAndAssetsOutput>();
        try {
            IncomeAndAssetsOutput output = incomeService.getIncomeAndAssets(cpf);
            response.setData(output);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
