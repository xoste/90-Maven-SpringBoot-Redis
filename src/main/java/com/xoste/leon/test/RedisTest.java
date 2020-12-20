package com.xoste.leon.test;


import com.xoste.leon.Application;
import com.xoste.leon.pojo.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Spring Data Redis 测试
 * @author Leon
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 添加一个字符串
     * */
    @Test
    public void setTest() {
        this.redisTemplate.opsForValue().set("key", "维特我都是方方法");
    }

    /**
    *   取出一个字符串
    * */
    @Test
    public void getTest() {
        Object key = this.redisTemplate.opsForValue().get("key");
        System.out.println(key);
    }

    /**
    * 添加Users对象
    * */
    @Test
    public void setUsersTest() {
        Users users = new Users();
        users.setId(1);
        users.setUsername("李四");
        users.setPassword("1234");
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Users.class));
        this.redisTemplate.opsForValue().set("users", users);
    }

    /**
     * 取出Users
     * */
    @Test
    public void getUsersTest() {
        this.redisTemplate.opsForValue().get("users");
        System.out.println(this.redisTemplate.opsForValue().get("users"));
    }
}

