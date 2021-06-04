package com.ruleta.zabala.apiruleta.api.modules.roulette.repository;

import com.ruleta.zabala.apiruleta.api.modules.roulette.entities.Roulette;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouletteRepository {
    private static final Object HEADER = "ROULETTE";
    private HashOperations hashOperations;
    private RedisTemplate redisTemplate;
    public RouletteRepository(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }
    public String save(Roulette entity) {
        hashOperations.put(HEADER, entity.getId(), entity);
        return entity.getId();
    }
    public List findAll(){
        return hashOperations.values(HEADER);
    }
    public Roulette findById(String key){
        return (Roulette) hashOperations.get(HEADER, key);
    }
    public Roulette updateStatus(Roulette entity){
        save(entity);
        return entity;
    }
}
