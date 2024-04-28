package com.liapkalo.profitsoft.filmwebapp.repository;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query(value = "SELECT new com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto(f.name, f.genre, f.director.id) " +
            "FROM Film f WHERE f.name LIKE %:name% OR f.genre LIKE %:genre% OR f.director.id = :directorId")
    Optional<Page<FilmNameAndGenreDto>> findAllByNameOrGenreOrDirector(String name, String genre, Long directorId,
                                                                      Pageable pageable);

    @Query(value = "SELECT new com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto(f.name, f.genre, f.director.id) " +
            "FROM Film f")
    Optional<Page<FilmNameAndGenreDto>> findAllFilmsNameAndGenreAndDirector(Pageable pageable);
}
