package com.ruleta.zabala.apiruleta.api.modules.user.services.impl;

import com.ruleta.zabala.apiruleta.api.modules.user.entities.User;
import com.ruleta.zabala.apiruleta.api.modules.user.repository.UserRepository;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends GenericService<String, User> {
    @Autowired
    UserRepository repository;

    @Override
    public String save(User entity) {
        entity.setId(UUID.randomUUID().toString().replace("-", ""));
        return repository.save(entity);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
