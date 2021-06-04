package com.ruleta.zabala.apiruleta.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:application.properties")

public class RedisConfiguration {

    @Value("${spring.redis.host}") String hostName;

    @Value("${spring.redis.port}") Integer port;

    @Value("${spring.redis.password}") String password;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostName, port);
        config.setPassword(password);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(60000);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        poolConfig.setNumTestsPerEvictionRun(-1);
        poolConfig.setMaxTotal(16);

        return new JedisConnectionFactory(config, JedisClientConfiguration.builder()
                .usePooling().poolConfig(poolConfig).build());
    }
    @Bean
    public StringRedisConnection stringRedisConnection() throws Exception {
        return new DefaultStringRedisConnection(redisConnectionFactory().getConnection());
    }
}
