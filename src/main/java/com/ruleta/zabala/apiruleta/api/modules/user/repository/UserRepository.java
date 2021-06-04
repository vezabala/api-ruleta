package com.ruleta.zabala.apiruleta.api.modules.user.repository;

import com.ruleta.zabala.apiruleta.api.modules.user.entities.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private static final Object HEADER = "USER";
    private HashOperations hashOperations;
    private RedisTemplate redisTemplate;

    public UserRepository(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public String save(User entity) {
        hashOperations.put(HEADER, entity.getId(), entity);
        return entity.getId();
    }

    public User findById(String key){
        return (User) hashOperations.get(HEADER, key);
    }

    public List findAll(){
        return hashOperations.values(HEADER);
    }

    public User updateStatus(User entity){
        save(entity);
        return entity;
    }

}
