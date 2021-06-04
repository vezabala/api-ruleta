package com.ruleta.zabala.apiruleta.api.modules.roulette.controllers;

import com.ruleta.zabala.apiruleta.api.controller.GenericController;
import com.ruleta.zabala.apiruleta.api.controller.RestResponse;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import com.ruleta.zabala.apiruleta.api.modules.roulette.enums.TypeEnum;
import com.ruleta.zabala.apiruleta.api.modules.roulette.services.impl.RouletteServiceImpl;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roulette")
public class RouletteController extends GenericController<String, Roulette> {
    @Autowired
    RouletteServiceImpl service;
    @Override
    protected GenericService<String, Roulette> getService() {
        return service;
    }
    @GetMapping("opening/{id}")
    @ResponseBody
    ResponseEntity<RestResponse<Roulette>> opening(@PathVariable("id") String id){
        Roulette entity = null;
        try {
            entity = service.opening(id);
        } catch (Exception e) {

            return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return buildResponse("Se ha realizado correctamente la apertura de la ruleta", HttpStatus.OK, entity);
    }
    @GetMapping("closing/{id}")
    @ResponseBody
    ResponseEntity<RestResponse<Roulette>> closing(@PathVariable("id") String id){
        Roulette entity = null;
        try {
            entity = service.closing(id);
        } catch (Exception e) {
            return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return buildResponse("Se ha realizado correctamente el cierre de la ruleta", HttpStatus.OK, entity);
    }
    @GetMapping({"bet/{id}/{type}/{value}"})
    @ResponseBody
    ResponseEntity<RestResponse<Roulette>> bet(@PathVariable(value = "id", required = true) String id,
                                               @PathVariable(value = "type", required = true) String type,
                                               @PathVariable(value = "value", required = true) String value){
        Roulette entity;
        TypeEnum typeEnum = isValidType(type);
        try {
            if(type != null && typeEnum != null)
                entity = service.bet(id,value,typeEnum);
            else
                throw new Exception("El tipo de valor ingresado no coindice con lo esperado {color, number}");
        } catch (Exception e) {
            return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return buildResponse("Se ha realizado correctamente el cierre de la ruleta", HttpStatus.OK, entity);
    }
    private TypeEnum isValidType(String type) {
        return TypeEnum.valueOf(type.toUpperCase());
    }
}
