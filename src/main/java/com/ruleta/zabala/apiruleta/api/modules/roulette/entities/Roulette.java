package com.ruleta.zabala.apiruleta.api.modules.roulette.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

@RedisHash("Roulette")
public class Roulette implements Serializable{
    private static final long serialVersionUID = -422583128045078934L;
    @Id
    private String id;
    private Integer number;
    private Boolean status;
    private List<Bet> bet = new ArrayList<>();
    public Roulette(){
    }
    public String getId() {

        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Boolean getStatus() {

        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public List<Bet> getBet() {
        return bet;
    }
    public void setBet(List<Bet> bet) {
        this.bet = bet;
    }
}