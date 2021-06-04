package com.ruleta.zabala.apiruleta.api.controller;

import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public abstract class GenericController<HK, HV> {

    protected abstract GenericService<HK, HV> getService();

    @PostMapping()
    String create(@RequestBody HV input){
        getService().save(input);
        return "Hello!";
    }

    @GetMapping()
    Map<HK, HV> list(){
        System.out.println(getService().findAll());
        return getService().findAll();
    }
}
