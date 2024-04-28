package com.liapkalo.profitsoft.filmwebapp.utils;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class ListUtils {

    public ListUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Map<String, Object> createResponseList(Page<FilmNameAndGenreDto> films) {
        Map<String, Object> response = new HashMap<>();
        response.put("films", films.getContent());
        response.put("totalPages", films.getTotalPages());
        return response;
    }
}
