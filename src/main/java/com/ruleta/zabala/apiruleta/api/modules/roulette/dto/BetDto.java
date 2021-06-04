package com.ruleta.zabala.apiruleta.api.modules.roulette.dto;

public class BetDto {
    private Integer number = 0;
    private String color = "";
    private double money = 0;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}