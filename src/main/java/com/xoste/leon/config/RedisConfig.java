package com.xoste.leon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 完成对redis的整合的一些配置
 * @author Leon
 */
@Configuration
public class RedisConfig {
    /**
    * 1.创建JedisPoolConfig对象，再该对象中完成一些连接池的配置
    * */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.jedis.pool")
    public JedisPoolConfig  jedisPoolingConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        /*// 最大空闲数
        jedisPoolConfig.setMaxIdle(10);
        // 最小空闲数
        jedisPoolConfig.setMinIdle(5);
        // 最大连接数
        jedisPoolConfig.setMaxTotal(20);*/
        return jedisPoolConfig;
    }

    /**
     * 2.创建JedisConnectionFactory对象, 配置redis的连接信息
     * */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        // 关联连接池的配置对象
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        // 配置主机地址
        /*jedisConnectionFactory.setHostName("192.168.91.133");
        // 端口
        jedisConnectionFactory.setPort(6379);*/
        return jedisConnectionFactory;
    }

    /**
    * 3.创建RedisTemplate对象
    * */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 关联jedis
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        // 为key设置序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 为value设置序列化器
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
