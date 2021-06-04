package com.ruleta.zabala.apiruleta.api.modules.roulette.services.impl;

import com.ruleta.zabala.apiruleta.api.service.GenericService;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import com.ruleta.zabala.apiruleta.api.modules.roulette.repository.RouletteRepository;
import com.ruleta.zabala.apiruleta.api.modules.roulette.services.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RouletteServiceImpl extends GenericService<String, Roulette> implements RouletteService {
    @Autowired
    RouletteRepository repository;
    public static final String HEADER = "ROULETTE";
    @Override
    public void save(Roulette entity) {
        repository.save(entity);
    }
    @Override
    public Map<String, Roulette> findAll() {
        return (Map<String, Roulette>) repository.findAll();
    }
}
