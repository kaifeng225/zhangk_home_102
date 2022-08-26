package com.example.springboot.json;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ObjectMapperSingleton {
    private ObjectMapperSingleton() {

    }

    public static ObjectMapper getInstance() {
        return ObjectMapperHolder.INSTANCE;
    }

    private static class ObjectMapperHolder {
        private static final ObjectMapper INSTANCE = create();

        private static ObjectMapper create() {
            ObjectMapper instance = new ObjectMapper();
            instance.findAndRegisterModules();
            instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            instance.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            instance.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
            instance.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
            instance.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(String.class, new StringTrimDeserializer());
            instance.registerModules(new JavaTimeModule(), module);
            return instance;
        }
    }

    public static String writeValueAsString(Object value) {
        try {
            return getInstance().writeValueAsString(value);
        } catch (Exception e) {
            log.warn("write json as string exception:", e);
            throw new JsonProcessingException(e);
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            log.warn("read json as object exception:", e);
            throw new JsonProcessingException(e);
        }
    }

    public static Map<String, String> readStringValueMap(String content) {
        return readValue(content, TypeFactory.defaultInstance().constructMapType(Map.class, String.class, String.class));
    }
    public static Set<String> readStringArrays(String content) {
        return readValue(content, TypeFactory.defaultInstance().constructParametricType(Set.class, String.class));
    }

    public static Map<String, Object> readObjectValueMap(String content) {
        return readValue(content, TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class));
    }

    public static List<Map<String, Object>> readObjectValueMapList(String content) {
        JavaType parametricType = TypeFactory.defaultInstance().constructParametricType(List.class,
          TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class));
        return readValue(content, parametricType);
    }

    public static <T> T readValue(String content, JavaType valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            throw new JsonProcessingException(e);
        }
    }
}
