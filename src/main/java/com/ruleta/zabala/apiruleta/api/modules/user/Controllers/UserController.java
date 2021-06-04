package com.ruleta.zabala.apiruleta.api.modules.user.Controllers;

import com.ruleta.zabala.apiruleta.api.controller.GenericController;
import com.ruleta.zabala.apiruleta.api.modules.user.entities.User;
import com.ruleta.zabala.apiruleta.api.modules.user.services.impl.UserServiceImpl;
import com.ruleta.zabala.apiruleta.api.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends GenericController<String, User> {

    @Autowired
    UserServiceImpl service;

    @Override
    protected GenericService<String, User> getService() {
        return service;
    }
}
