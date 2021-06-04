package com.ruleta.zabala.apiruleta.api.modules.roulette.entities;

import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("scores")
public class Bet implements Serializable {
    private static final long serialVersionUID = 7821110451301315929L;
    private String value;
    private Double cash;
    public Bet(String value, Double cash) {
        this.value = value;
        this.cash = cash;
    }
    public String getValue() {

        return value;
    }
    public void setValue(String value) {

        this.value = value;
    }
    public Double getCash() {

        return cash;
    }
    public void setCash(Double cash) {
        this.cash = cash;
    }
}
