package com.liapkalo.profitsoft.filmwebapp.service;

import com.fasterxml.jackson.core.JsonParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ImportFilmService {

    Map<String, Integer> importFilmsFromJson(MultipartFile file);

    Map<String, Integer> processFilmsFromJson(JsonParser parser) throws IOException;
}
