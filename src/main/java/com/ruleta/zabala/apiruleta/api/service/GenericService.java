package com.ruleta.zabala.apiruleta.api.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class GenericService <HK,HV> {
    public abstract String save(HV entity);
    public abstract List<HV> findAll();
}
