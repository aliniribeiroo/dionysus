package com.aliniribeiro.dionysus.controller.assets;

import com.aliniribeiro.dionysus.controller.common.Response;
import com.aliniribeiro.dionysus.controller.person.PersonService;
import com.aliniribeiro.dionysus.controller.assets.contracts.PersonAssetsOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetsController {

    @Autowired
    PersonService personService;

    @GetMapping(value = "/dionysus/getCPFAssets/{cpf}/{page}/{size}")
    public ResponseEntity<Response<PersonAssetsOutput>> getLastSearch(@PathVariable String cpf, @PathVariable("page") Long page, @PathVariable("size") Long size) {
        Response<PersonAssetsOutput> response = new Response<PersonAssetsOutput>();
        try {
            PersonAssetsOutput output = personService.getCPFAssetsList(cpf, page, size);
            response.setData(output);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
