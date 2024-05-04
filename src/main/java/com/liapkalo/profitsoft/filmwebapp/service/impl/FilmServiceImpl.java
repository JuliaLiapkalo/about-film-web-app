package com.liapkalo.profitsoft.filmwebapp.service.impl;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import com.liapkalo.profitsoft.filmwebapp.entity.mapper.FilmMapper;
import com.liapkalo.profitsoft.filmwebapp.repository.FilmRepository;
import com.liapkalo.profitsoft.filmwebapp.service.ActorService;
import com.liapkalo.profitsoft.filmwebapp.service.DirectorService;
import com.liapkalo.profitsoft.filmwebapp.service.FilmService;
import com.liapkalo.profitsoft.filmwebapp.specification.FilmSpecifications;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilmServiceImpl implements FilmService {

    FilmRepository filmRepository;
    DirectorService directorService;
    ActorService actorService;
    FilmMapper filmMapper;

    /**
     * This method creates a new film using the provided FilmDto and saves it to the database.
     *
     * @param filmDto The data transfer object containing information about the film to be created.
     * @return The created film saved to the database.
     */
    @Transactional
    @Override
    public Film createFilm(FilmDto filmDto) {
        log.info("Creating film: {}", filmDto);
        return filmRepository.save(addFilm(filmDto));
    }

    /**
     * This method retrieves the film with the specified ID from the database.
     *
     * @param id The ID of the film to be retrieved.
     * @return The film retrieved from the database.
     * @throws NoSuchElementException if no film with the given ID is found in the database.
     */
    @Override
    public Film getFilm(Long id) {
        log.info("Getting film: {}", id);
        return filmRepository.findById(id).orElseThrow();
    }

    /**
     * Updates the information of a film identified by the given ID using the provided FilmDto object.
     * This method first retrieves the film from the database based on the provided ID.
     * If the film with the given ID is found, its information is updated with the values provided in the FilmDto object,
     * and the updated film is saved back to the database.
     * If no film with the given ID is found, an IllegalArgumentException is thrown.
     *
     * @param id       The ID of the film to be updated.
     * @param filmDto  The data transfer object containing the updated information for the film.
     * @return The updated film.
     * @throws IllegalArgumentException if no film with the given ID is found in the database.
     */
    @Transactional
    @Override
    public Film updateFilm(Long id, FilmDto filmDto) {
        log.info("Updating film: {}", filmDto);

        Film existingFilm = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film with id " + id + " not found"));

        updateFilmFields(existingFilm, filmDto);

        return filmRepository.save(existingFilm);
    }

    /**
     * This method deletes the film with the specified ID from the database.
     *
     * @param id The ID of the film to be deleted.
     * @return A message indicating the successful deletion of the film.
     */
    @Transactional
    @Override
    public String deleteFilm(Long id) {
        log.info("Deleting film: {}", id);

        filmRepository.deleteById(id);

        return "Film with id [" + id + "] deleted successfully!";
    }

    /**
     * Retrieves a page of films based on the provided criteria.
     * This method retrieves films from the database that match the provided name, genre, or director ID.
     * If the provided filmNameAndGenreDto is not null, it filters films based on the criteria provided in the object.
     * If the filmNameAndGenreDto is null, it retrieves all films.
     * It returns a paginated result based on the provided pageable object.
     *
     * @param filmFilterDto The data transfer object containing criteria for filtering films by name, genre, and director ID.
     * @param pageable      The pagination information specifying the page number, page size, sorting, etc.
     * @return A page containing films that match the provided criteria, or all films if no criteria are provided.
     * @throws NoSuchElementException if no films matching the criteria are found in the database.
     */
    @Override
    public Map<String, Object> getFilteredFilmsByPage(FilmFilterDto filmFilterDto, Pageable pageable) {
        log.info("Getting films from page: {}", pageable.getPageNumber());

        return createResponseList(filmRepository.findAll(FilmSpecifications.filterFilm(filmFilterDto), pageable));
    }

    /**
     * Retrieves a list of films based on the provided criteria.
     *
     * @param filmFilterDto The data transfer object containing criteria for filtering films by name, genre, and director ID.
     */
    @Override
    public List<Film> getFilteredFilms(FilmFilterDto filmFilterDto) {
        return filmRepository.findAll(FilmSpecifications.filterFilm(filmFilterDto));
    }

    private Map<String, Object> createResponseList(Page<Film> films) {
        Map<String, Object> response = new HashMap<>();
        response.put("films", films.getContent().stream()
                .map(filmMapper::toFilmFilterDto)
                .collect(Collectors.toList()));
        response.put("totalPages", films.getTotalPages());
        return response;
    }

    private Film addFilm(FilmDto filmDto) {
        Film film = filmMapper.toFilm(filmDto);
        Director director = directorService.getOrCreateDirector(filmDto.getDirector());
        film.setDirector(director);
        List<Actor> actors = actorService.getOrCreateActors(filmDto.getMainActors());
        film.setMainActors(actors);
        return film;
    }

    /** (METHOD TO HAVE ABILITY UPDATE ONE FILED) */
    private void updateFilmFields(Film film, FilmDto filmDto) {
        if (Objects.nonNull(filmDto.getName()) && !filmDto.getName().isBlank()) {
            film.setName(filmDto.getName());
        }
        if (Objects.nonNull(filmDto.getGenre()) && !filmDto.getGenre().isBlank()) {
            film.setGenre(film.getGenre());
        }
        if (Objects.nonNull(filmDto.getReleaseYear()) &&
                Year.now().isAfter(Year.of(filmDto.getReleaseYear()))) {
            film.setReleaseYear(filmDto.getReleaseYear());
        }

        updateMainActors(film, filmDto.getMainActors());
        updateDirector(film, filmDto.getDirector());
    }

    private void updateMainActors(Film film, List<ActorDto> actors) {
        if (Objects.nonNull(actors)) {
            film.setMainActors(actors.stream()
                    .map(actorDto -> actorService.getOrCreateActor(new ActorDto(actorDto.getName())))
                    .collect(Collectors.toList()));
        }
    }

    private void updateDirector(Film film, DirectorDto director) {
        if (Objects.nonNull(director)) {
            if (!director.getName().isEmpty() && !director.getName().isBlank() ||
                director.getAge() > 0) {
                film.setDirector(directorService.getOrCreateDirector(new DirectorDto(director.getName(), director.getAge())));
            }
        }
    }

}
