package com.ruleta.zabala.apiruleta.api.modules.roulette.controllers;

import com.ruleta.zabala.apiruleta.api.controller.GenericController;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import com.ruleta.zabala.apiruleta.api.modules.roulette.services.impl.RouletteServiceImpl;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roulette")
public class RouletteController extends GenericController<String, Roulette> {

    @Autowired
    RouletteServiceImpl service;

    @Override
    protected GenericService getService() {
        return service;
    }
}
