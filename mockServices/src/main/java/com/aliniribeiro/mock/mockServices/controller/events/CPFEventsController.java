package com.aliniribeiro.mock.mockServices.controller.events;


import com.aliniribeiro.mock.mockServices.controller.common.Response;
import com.aliniribeiro.mock.mockServices.controller.debts.DebtsService;
import com.aliniribeiro.mock.mockServices.controller.debts.contracts.DebtsOutput;
import com.aliniribeiro.mock.mockServices.controller.events.contracts.EventsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CPFEventsController {


    @Autowired
    private CPFEventsService cpfEventsService;

    /**
     * API que retorna todas as dividas da base de dados
     * @return
     */
    @GetMapping(value = "/mockC/getCPFEvents/{cpf}")
    public ResponseEntity<Response<EventsOutput>> getCPFEvents(@PathVariable String cpf) {
        Response<EventsOutput> response = new Response<EventsOutput>();
        try {
            EventsOutput output = cpfEventsService.getEventsOutput(cpf);
            response.setData(output);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
