package com.ruleta.zabala.apiruleta.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
public abstract class ControllerGenericResponse <HV>{
    public ResponseEntity<RestResponse<HV>> buildResponse(String value, HttpStatus status, HV obj ){
        return new ResponseEntity<>(
                new RestResponse<>(status.value(), true, null, value , obj),
                HttpStatus.OK);
    }

    public ResponseEntity<RestResponse<HV>> buildResponse(String value, HttpStatus status){
        return buildResponse(value, status, null);
    }
}
