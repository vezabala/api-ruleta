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
    /**
     * {@code GET /opening/:id}: open the roulette opening
     * @param id of entity Roulette
     * @param response
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roulette, or with status {@code 404 (Not Found)}.
     */
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
    /**
     * {@code GET /closing/:id}: open the roulette closing
     * @param id of entity Roulette
     * @param response
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roulette, or with status {@code 404 (Not Found)}.
     */
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
    /**
     * {@code GET /bet/:id}: open the bet
     * @param id of entity Roulette, in body the betDto and header user-id
     * @param response
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roulette, or with status {@code 404 (Not Found)}.
     */
    @GetMapping({"/bet/{id}"})
    @ResponseBody
    ResponseEntity<RestResponse<Roulette>> bet(@PathVariable(value = "id", required = true) String id,
                                               @RequestBody BetDto betDto,
                                               @RequestHeader(value = "user-id", required = true) String userId,
                                               HttpServletResponse response){
        Roulette entity;
        try {
            entity = service.bet(id,betDto, userId);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        response.setStatus(HttpStatus.CREATED.value());
        return buildResponse("Se ha realizado correctamente la apuesta", HttpStatus.OK, entity);
    }
}
