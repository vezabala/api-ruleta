package com.ruleta.zabala.apiruleta.api.modules.roulette.services.impl;

import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Bet;
import com.ruleta.zabala.apiruleta.api.modules.roulette.enums.TypeEnum;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import com.ruleta.zabala.apiruleta.api.modules.roulette.repository.RouletteRepository;
import com.ruleta.zabala.apiruleta.api.modules.roulette.services.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class RouletteServiceImpl extends GenericService<String, Roulette> implements RouletteService {
    public static final String ROJO = "rojo";
    public static final String NEGRO = "negro";
    @Autowired
    RouletteRepository repository;
    @Override
    public String save(Roulette entity) {
        entity.setId(UUID.randomUUID().toString().replace("-", ""));
        return repository.save(entity);
    }
    @Override
    public List<Roulette> findAll() {
        return repository.findAll();
    }
    public Roulette bet(String key, String value, TypeEnum type) throws Exception {
        Roulette roulette = repository.findById(key);
        if(!validateValueByType(value, type))
            throw new Exception("Los valores ingresados no son válidos");
        if(roulette.getStatus() == Boolean.FALSE)
            throw new Exception("La ruleta está cerrada");
        if(key == null)
            throw new Exception("La llave está vacía");
        if(value == null)
            throw new Exception("El valor a apostar no puede estar vacío");
        Bet bet = new Bet(value, 500.0);
        validateScoreArray(roulette, bet);
        repository.save(roulette);
        return roulette;
    }
    private boolean validateValueByType(String value, TypeEnum typeEnum){
        if(typeEnum.equals(TypeEnum.NUMBER)){
            int number = Integer.parseInt(value);
            return number >= 0 && number <= 36;
        }
        return value.equals(ROJO) || value.equals(NEGRO);
    }
    private void validateScoreArray(Roulette roulette, Bet bet) {
        if(roulette.getBet().isEmpty() || roulette.getBet() == null){
            LinkedList list = new LinkedList<>();
            list.add(bet);
                roulette.setBet(list);
        }else
            roulette.getBet().add(bet);
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
