package com.example.myschool.commons.core.json;

import jakarta.websocket.EncodeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.example.myschool.config.JsonMapper.getObjectMapper;
import static com.example.myschool.commons.core.json.Json.decodeValue;

@Slf4j
public class JsonObject {
    private Map<String, Object> map;
    private String json;

    public JsonObject(String json) {
        this.json = json;
        fromJson(json);
    }

    public JsonObject(Map<String, Object> map) {
        this.map = map;
    }

    private void fromJson(String json) {
        try {
            map = decodeValue(json, Map.class);
        } catch (Exception exception) {
            log.error("[JSON-OBJECT] can't decode value ", exception);
        }
    }

    public static JsonObject mapFrom(Object obj) {
        Map<String, Object> map = (Map<String, Object>) getObjectMapper().convertValue(obj, Map.class);
        return new JsonObject(map);
    }

    public String encode() {
        return Json.encode(map);
    }

    public <T> T mapTo(Class<T> type) {
        if (map.isEmpty()) return null;
        return getObjectMapper().convertValue(map, type);
    }


}
