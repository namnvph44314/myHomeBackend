package com.example.myschool.commons.core.json;

import com.example.myschool.commons.core.exception.DecodeException;
import com.example.myschool.commons.core.exception.EncodeException;
import com.fasterxml.jackson.core.JsonProcessingException;

import static com.example.myschool.config.JsonMapper.getObjectMapper;

public class Json {
    public static <T> T decodeValue(String str, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(str, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DecodeException("Failed to decode: " + e.getMessage());
        }
    }

    public static String encode(Object obj) throws EncodeException {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new EncodeException("Failed to encode as JSON: " + e.getMessage());
        }
    }
}
