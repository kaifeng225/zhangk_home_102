package com.example.springboot.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class StringTrimDeserializer extends StdDeserializer<String> {

    public StringTrimDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        return StringUtils.trim(StringDeserializer.instance.deserialize(p, ctx));
    }
}