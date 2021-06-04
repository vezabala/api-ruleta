package com.ruleta.zabala.apiruleta.api.modules.roulette.entities;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Roulette")
public class Roulette implements Serializable{
    private String id;
    private Integer number;
    private Boolean status;
    public String getId() {

        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getNumber() {

        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public Boolean getStatus() {

        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
}