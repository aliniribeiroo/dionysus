package com.aliniribeiro.dionysus.controller.events;

import com.aliniribeiro.dionysus.controller.common.Response;
import com.aliniribeiro.dionysus.controller.events.contracts.CredictCardPurchaseDTO;
import com.aliniribeiro.dionysus.controller.events.contracts.FinancialMovementDTO;
import com.aliniribeiro.dionysus.controller.events.contracts.LastSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {

    @Autowired
    EventsService eventsService;

    @GetMapping(value = "/dionysus/getLastSearch/{cpf}")
    public ResponseEntity<Response<LastSearchDTO>> getLastSearch(@PathVariable String cpf) {
        Response<LastSearchDTO> response = new Response<LastSearchDTO>();
        try {
            LastSearchDTO output = eventsService.getLastSearch(cpf);
            response.setData(output);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/dionysus/getFinancialMovement/{cpf}")
    public ResponseEntity<Response<List<FinancialMovementDTO>>> getFinancialMovement(@PathVariable String cpf) {
        Response<List<FinancialMovementDTO>> response = new Response<List<FinancialMovementDTO>>();
        try {
            List<FinancialMovementDTO> output = eventsService.getFinancialMovement(cpf);
            response.setData(output);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/dionysus/getLastCredictCardPurchase/{cpf}")
    public ResponseEntity<Response<CredictCardPurchaseDTO>> getLastCredictCardPurchase(@PathVariable String cpf) {
        Response<CredictCardPurchaseDTO> response = new Response<CredictCardPurchaseDTO>();
        try {
            CredictCardPurchaseDTO output = eventsService.getLastCredictCardPurchase(cpf);
            response.setData(output);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
