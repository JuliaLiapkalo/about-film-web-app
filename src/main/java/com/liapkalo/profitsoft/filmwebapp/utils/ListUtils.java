package com.liapkalo.profitsoft.filmwebapp.utils;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class ListUtils {

    public ListUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Map<String, Object> createResponseList(Page<Film> films) {
        Map<String, Object> response = new HashMap<>();
        response.put("films", films.getContent().stream()
                .map(f -> new FilmFilterDto(f.getName(), f.getGenre(), f.getDirector())).toList());
        response.put("totalPages", films.getTotalPages());
        return response;
    }
}
