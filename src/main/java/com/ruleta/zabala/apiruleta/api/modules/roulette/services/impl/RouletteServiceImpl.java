package com.ruleta.zabala.apiruleta.api.modules.roulette.services.impl;

import com.ruleta.zabala.apiruleta.api.helpers.RandomValueGenerator;
import com.ruleta.zabala.apiruleta.api.modules.roulette.dto.BetDto;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Bet;
import com.ruleta.zabala.apiruleta.api.modules.roulette.enums.TypeEnum;
import com.ruleta.zabala.apiruleta.api.modules.user.entities.User;
import com.ruleta.zabala.apiruleta.api.modules.user.repository.UserRepository;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import com.ruleta.zabala.apiruleta.api.modules.roulette.repository.RouletteRepository;
import com.ruleta.zabala.apiruleta.api.modules.roulette.services.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class RouletteServiceImpl extends GenericService<String, Roulette> implements RouletteService {
    public static final String ROJO = "rojo";
    public static final String NEGRO = "negro";
    @Autowired
    RouletteRepository repository;
    @Autowired
    UserRepository userRepository;
    @Override
    public String save(Roulette entity) {
        if(entity == null){
            entity = new Roulette();
            entity.setStatus(Boolean.FALSE);
        }
        entity.setId(UUID.randomUUID().toString().replace("-", ""));
        return repository.save(entity);
    }
    @Override
    public List<Roulette> findAll() {
        return repository.findAll();
    }
    public Roulette bet(String key, BetDto dto, String userId) throws Exception {
        User user = userRepository.findById(userId);
        TypeEnum type = this.isValidType(dto);
        Roulette roulette = repository.findById(key);
        if(user == null)
            throw new Exception("No se ha encontrado el usuario");
        if(roulette == null)
            throw new Exception("No se ha encontrado la ruleta");
        if(roulette.getStatus().equals(Boolean.FALSE))
            throw new Exception("La ruleta está cerrada");
        if(user.getBalance() < dto.getMoney())
            throw new Exception("El usuario no posee fondo suficiente para apostar");
        if(!(dto.getMoney() >= 1 && dto.getMoney() <= 10000))
            throw new Exception("El rango en dinero para apuesta no está en el rango (entre 0 y 10000)");
        if(!validateValueByType(dto.getNumber(),dto.getColor(), type))
            throw new Exception("Los valores ingresados no son válidos");
        if(key == null)
            throw new Exception("La llave está vacía");
        validateScoreArray(roulette, buildRouletteBet(dto.getNumber(),dto.getColor(),dto.getMoney(),type,user));
        repository.save(roulette);
        return roulette;
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
    private Bet buildRouletteBet(Integer number, String color, Double money, TypeEnum typeEnum, User user){
        String resultValue = typeEnum.equals(TypeEnum.NUMBER) ?
                String.valueOf(RandomValueGenerator.generateRandomNumber()) :
                RandomValueGenerator.generateRandomColor().getValue();
        String value = typeEnum.equals(TypeEnum.NUMBER) ?
                String.valueOf(number) :
                color;
        if(value.equalsIgnoreCase(resultValue))
            user.setBalance(user.getBalance() + money);
        else
            user.setBalance(user.getBalance() - money);
        userRepository.save(user);
        return new Bet(value, resultValue, money, user);
    }
    private boolean validateValueByType(Integer number, String color, TypeEnum typeEnum){
        if(typeEnum.equals(TypeEnum.NUMBER)){
            return number >= 0 && number <= 36;
        }
        return color.equals(ROJO) || color.equals(NEGRO);
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
        if(roulette.getStatus().equals(Boolean.TRUE))
            throw  new Exception("La ruleta ya se encontraba activa");
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
        if(roulette.getStatus().equals(Boolean.FALSE))
            throw new Exception("La ruleta se encontraba cerrada");
        roulette.setStatus(Boolean.FALSE);
        repository.updateStatus(roulette);
        return roulette;
    }
}
