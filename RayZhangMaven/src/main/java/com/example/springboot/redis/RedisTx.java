package com.example.springboot.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisTx {

    private final StringRedisTemplate redisTemplate;

    private final RedisScript<String> getThenDeleteScript;

    public <T> T getThenDelete(String key, Function<Optional<String>, T> function) {
        String val = redisTemplate.execute(getThenDeleteScript, Collections.singletonList(key));
        return function.apply(Optional.ofNullable(val));
    }
}
