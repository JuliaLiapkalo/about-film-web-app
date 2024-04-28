package com.liapkalo.profitsoft.filmwebapp.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonParseUtils {

    public JsonParseUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void validateJson(JsonParser parser) throws IOException {
        if (parser.nextToken() != JsonToken.START_ARRAY) {
            log.error("Input JSON is not an array.");
        }
    }
}
