package com.ruleta.zabala.apiruleta.api.controller;

import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<HK, HV> extends ControllerGenericResponse<HV> {

    protected abstract GenericService<HK, HV> getService();

    @PostMapping()
    @ResponseBody
    ResponseEntity<RestResponse<HV>> create(@RequestBody HV input) {
        String id = getService().save(input);
        return buildResponse(id, HttpStatus.OK);
    }

    @GetMapping()
        List<HV> list(){
        System.out.println(getService().findAll());
        return getService().findAll();
    }
}
