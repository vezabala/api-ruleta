package com.ruleta.zabala.apiruleta.api.modules.roulette.enums;

public enum ColorEnum {
    ROJO("rojo"),
    NEGRO("negro");

    private String value;
    ColorEnum(String s){
        value = s;
    }

    public String getValue(){
        return value;
    }
}
