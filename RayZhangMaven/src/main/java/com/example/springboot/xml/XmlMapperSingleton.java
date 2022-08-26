package com.example.springboot.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Andy Jiang on 5/11/21.
 */
@Slf4j
public class XmlMapperSingleton {

    private XmlMapperSingleton() {
    }

    public static XmlMapper getInstance() {
        return ObjectMapperHolder.INSTANCE;
    }

    private static class ObjectMapperHolder {
        private static final XmlMapper INSTANCE = create();

        private static XmlMapper create() {
            return new XmlMapper();
        }
    }

    public static Object readValue(String content) {
        try {
            return getInstance().readValue(content, Object.class);
        } catch (Exception e) {
            log.warn("read xml as object exception:", e);
            throw new XmlProcessingException(e);
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        } catch (Exception e) {
            log.warn("read xml as object exception:", e);
            throw new XmlProcessingException(e);
        }
    }

}
