package com.liapkalo.profitsoft.filmwebapp.controller;

import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.service.DirectorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/directors")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DirectorController {

    DirectorService directorService;

    @GetMapping
    public ResponseEntity<?> getAllDirectors() {
        return ResponseEntity.ok(directorService.getDirectors());
    }

    @PostMapping
    public ResponseEntity<?> addDirector(@Validated @RequestBody DirectorDto directorDto) {
        return ResponseEntity.ok(directorService.getOrCreateDirector(directorDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDirector(@PathVariable("id") Long id, @RequestBody DirectorDto directorDto) {
        return ResponseEntity.ok(directorService.updateDirector(id, directorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable("id") Long id) {
        return ResponseEntity.ok(directorService.deleteDirector(id));
    }
}
