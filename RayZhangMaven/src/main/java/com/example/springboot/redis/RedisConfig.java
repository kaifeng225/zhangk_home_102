package com.example.springboot.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

@Slf4j
@Configuration
public class RedisConfig {

    private static final String LUA_SCRIPT_DIR = "/scripts/lua/";

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnBean(value = RedisSentinelConfiguration.class)
    public LettuceConnectionFactory lettuceConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration) {
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
          .readFrom(ReadFrom.REPLICA_PREFERRED)
          .build();

        return new LettuceConnectionFactory(redisSentinelConfiguration, clientConfiguration);
    }

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }



    @Bean
    RedisScript<Boolean> releaseLockScript() {
        DefaultRedisScript<Boolean> releaseLockScript = new DefaultRedisScript<>();
        releaseLockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(LUA_SCRIPT_DIR + "release_lock.lua")));
        releaseLockScript.setResultType(Boolean.class);
        return releaseLockScript;
    }

    @Bean
    RedisScript<String> getThenDeleteScript() {
        DefaultRedisScript<String> getThenDeleteScript = new DefaultRedisScript<>();
        getThenDeleteScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(LUA_SCRIPT_DIR +
          "get_then_delete.lua")));
        getThenDeleteScript.setResultType(String.class);
        return getThenDeleteScript;
    }
    
    @Bean
    RedisScript<String> increaseSoapClientScoreScript() {
        DefaultRedisScript<String> increaseSoapClientScore = new DefaultRedisScript<>();
        increaseSoapClientScore.setScriptSource(new ResourceScriptSource(new ClassPathResource(LUA_SCRIPT_DIR +
          "increase_soap_client_score.lua")));
        increaseSoapClientScore.setResultType(String.class);
        return increaseSoapClientScore;
    }
}
