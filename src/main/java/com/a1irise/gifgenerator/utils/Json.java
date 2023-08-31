package com.a1irise.gifgenerator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode parse(String src) throws JsonProcessingException {
        return objectMapper.readTree(src);
    }
}
