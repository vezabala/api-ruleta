package com.ruleta.zabala.apiruleta.api.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class GenericService <HK,HV> {
    public abstract void save(HV entity);
    public abstract Map<HK, HV> findAll();
}
