package com.example.springboot.json;

import java.io.IOException;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

public class ZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime>{

	public ZonedDateTimeDeserializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        return null;
    }
}
