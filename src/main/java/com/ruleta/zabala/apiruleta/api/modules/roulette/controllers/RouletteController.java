package com.ruleta.zabala.apiruleta.api.modules.roulette.controllers;

import com.ruleta.zabala.apiruleta.api.controller.GenericController;
import com.ruleta.zabala.apiruleta.api.controller.RestResponse;
import com.ruleta.zabala.apiruleta.api.modules.roulette.dto.BetDto;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import com.ruleta.zabala.apiruleta.api.modules.roulette.enums.TypeEnum;
import com.ruleta.zabala.apiruleta.api.modules.roulette.services.impl.RouletteServiceImpl;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    ResponseEntity<RestResponse<Roulette>> opening(@PathVariable("id") String id, HttpServletResponse response){
        Roulette entity = null;
        try {
            entity = service.opening(id);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());

            return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return buildResponse("Se ha realizado correctamente la apertura de la ruleta", HttpStatus.OK, entity);
    }
    @GetMapping("closing/{id}")
    @ResponseBody
    ResponseEntity<RestResponse<Roulette>> closing(@PathVariable("id") String id, HttpServletResponse response){
        Roulette entity = null;
        try {
            entity = service.closing(id);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return buildResponse("Se ha realizado correctamente el cierre de la ruleta", HttpStatus.OK, entity);
    }
    @GetMapping({"/bet/{id}"})
    @ResponseBody
    ResponseEntity<RestResponse<Roulette>> bet(@PathVariable(value = "id", required = true) String id,
                                               @RequestBody @Validated BetDto betDto,
                                               HttpServletResponse response){
        Roulette entity;
        TypeEnum typeEnum;
        try {
            typeEnum = isValidType(betDto);
            entity = service.bet(id,betDto.getNumber(), betDto.getColor(),typeEnum, betDto.getMoney());
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        response.setStatus(HttpStatus.CREATED.value());
        return buildResponse("Se ha realizado correctamente el cierre de la ruleta", HttpStatus.OK, entity);
    }
    private TypeEnum isValidType(BetDto betDto) throws Exception {
        if(StringUtils.isEmpty(String.valueOf(betDto.getNumber())) && StringUtils.isEmpty(betDto.getColor()))
            throw new Exception("No se ha ingresado el TIPO esperado {color} o {number}");
        else if(betDto.getNumber() > 0 && !StringUtils.isEmpty(betDto.getColor()))
            throw new Exception("Se espera solo un TIPO {color} o {number}");
        else if(!StringUtils.isEmpty(betDto.getColor()))
            return TypeEnum.COLOR;
        else if(betDto.getNumber() > 0)
            return TypeEnum.NUMBER;
        throw new Exception("No se ha ingresado un tipo de dato esperado.");
    }
}
