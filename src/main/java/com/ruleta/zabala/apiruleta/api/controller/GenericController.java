package com.ruleta.zabala.apiruleta.api.controller;

import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class GenericController<HK, HV> extends ControllerGenericResponse<HV> {

    protected abstract GenericService<HK, HV> getService();

    /**
     * {@code POST  /"entity"} : Create a new roulette or user
     * @param input
     * @param response
     * @return @return the {@link ResponseEntity} with status {@code 201 (Created)}
     */
    @PostMapping
    @ResponseBody
    ResponseEntity<RestResponse<HV>> create(@RequestBody(required = false) HV input, HttpServletResponse response){
        String id;
        try {
            id = getService().save(input);
        } catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return buildResponse("¡Vaya! no se ha podido insertar la entidad", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return buildResponse(id, HttpStatus.CREATED);
    }

    @GetMapping
        List<HV> list(){
        System.out.println(getService().findAll());
        return getService().findAll();
    }
}
