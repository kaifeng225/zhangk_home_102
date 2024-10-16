package com.example.springboot.redis;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    public static final String REDIS_SOAP_CLIENT_RANK = "SOAP_CLIENT_RANK";

    public static final String REDIS_KEY_SEPARATOR = ":";

    private final StringRedisTemplate redisTemplate;

    private final RedisScript<String> increaseSoapClientScoreScript;

    //redis过期时间以秒为单位
    private String expireTime = "30";

    @PostConstruct
    public void init() {
        expireTime = String.valueOf(TimeUnit.DAYS.toSeconds(7));
    }

    public void setRedisMessage(UUID uuid) {
        redisTemplate.opsForValue().set("zkftestredis", uuid.toString(), 300000, TimeUnit.MILLISECONDS);
    }

    public String getRedisMessage() {
        return redisTemplate.opsForValue().get("zkftestredis");
    }

    public void increaseClientScore(String client) {
        try {
            List<String> keys = Collections.singletonList(getRankKey(LocalDate.now()));
            log.info("keys====={}", keys.get(0).toString());
            String score = redisTemplate.execute(increaseSoapClientScoreScript, keys, client, expireTime);
            log.debug("client:{},current score is :{}", client, score);
        } catch (Exception e) {
            log.error("failure to increase client score", e);
        }
    }

    String getRankKey(LocalDate date) {
        return REDIS_SOAP_CLIENT_RANK + REDIS_KEY_SEPARATOR + date;
    }

}
