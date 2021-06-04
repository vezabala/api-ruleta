package com.ruleta.zabala.apiruleta.api.modules.roulette.services.impl;

import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Bet;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import com.ruleta.zabala.apiruleta.api.modules.roulette.repository.RouletteRepository;
import com.ruleta.zabala.apiruleta.api.modules.roulette.services.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RouletteServiceImpl extends GenericService<String, Roulette> implements RouletteService {
    @Autowired
    RouletteRepository repository;
    public static final String HEADER = "ROULETTE";
    @Override
    public String save(Roulette entity) {
        entity.setId(UUID.randomUUID().toString().replace("-", ""));
        return repository.save(entity);
    }
    @Override
    public List<Roulette> findAll() {
        return repository.findAll();
    }
    public Roulette bet(String key, String value) throws Exception {
        Roulette roulette = repository.findById(key);
        if(key == null)
            throw new Exception("La llave está vacía");
        if(roulette == null)
            throw new Exception("No existe la ruleta");
        if(value == null)
            throw new Exception("El valor a apostar no puede estar vacío");
        Bet score = new Bet(value, 500.0, roulette);
        roulette.getBet().add(score);
        repository.save(roulette);
        return roulette;
    }
    public Roulette opening(String key) throws Exception {
        Roulette roulette = repository.findById(key);
        if(key == null)
            throw new Exception("La llave está vacía");
        if(roulette == null)
            throw new Exception("No existe la ruleta");
        roulette.setStatus(Boolean.TRUE);
        repository.updateStatus(roulette);
        return roulette;
    }
    public Roulette closing(String key) throws Exception {
        Roulette roulette = repository.findById(key);
        if(key == null)
            throw new Exception("La llave está vacía");
        if(roulette == null)
            throw new Exception("No existe la ruleta");
        roulette.setStatus(Boolean.FALSE);
        repository.updateStatus(roulette);
        return roulette;
    }
}
