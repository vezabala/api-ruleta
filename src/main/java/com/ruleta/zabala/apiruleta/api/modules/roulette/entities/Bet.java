package com.ruleta.zabala.apiruleta.api.modules.roulette.entities;

import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("scores")
public class Bet implements Serializable {
    private String value;
    private Double cash;
    @Reference
    private Roulette rouletteId;
    public Bet(String value, Double cash, Roulette rouletteId) {
        this.value = value;
        this.cash = cash;
        this.rouletteId = rouletteId;
    }
}
