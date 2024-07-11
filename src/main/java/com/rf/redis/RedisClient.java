package com.rf.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${spring.redis.prefix}")
    private String prefix;

    @Value("${spring.redis.expired-seconds}")
    private Integer defaultExpiredSecond;


    public <T> T get(String key,Class<T> clazz){
        String str = redisTemplate.opsForValue().get(prefix+key);
        return stringToBean(str,clazz);
    }

    public void set(String key,Object value){
        String str = beanToString(value);
        redisTemplate.opsForValue().set(prefix+key,str,defaultExpiredSecond);
    }

    public void set(String key,Object value,int timeOut){
        String str = beanToString(value);
        redisTemplate.opsForValue().set(prefix+key,str,timeOut, TimeUnit.SECONDS);
    }

    public Long incr(String key){
        return redisTemplate.opsForValue().increment(prefix+key);
    }

    public void  expire(String key, Integer seconds) {
        redisTemplate.expire(prefix + key, Duration.ofSeconds(seconds));
    }

    public void expire(String key, int timeout) {
        redisTemplate.expire(prefix + key, timeout, TimeUnit.SECONDS);
    }
    

    private static <T> String beanToString(T value){
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == String.class){
            return (String) value;
        }

        if(clazz == Integer.class || clazz == int.class){
            return String.valueOf(value);
        }else if(clazz == Long.class || clazz == long.class){
            return String.valueOf(value);
        }else {
            return JSON.toJSONString(value);
        }
    }


    private <T> T stringToBean(String str,Class<T> clazz){
        if(str == null || clazz == null){
            return null;
        }
        if (clazz == String.class){
            return (T)str;
        }
        if(clazz == Integer.class || clazz == int.class){
            return (T)Integer.valueOf(str);
        }else if(clazz == Long.class || clazz == long.class){
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str.trim()),clazz);
        }
    }
}
