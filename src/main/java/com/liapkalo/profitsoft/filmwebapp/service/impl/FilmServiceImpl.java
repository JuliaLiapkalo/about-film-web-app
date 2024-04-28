package com.liapkalo.profitsoft.filmwebapp.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.ActorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmNameAndGenreDto;
import com.liapkalo.profitsoft.filmwebapp.repository.FilmRepository;
import com.liapkalo.profitsoft.filmwebapp.service.ActorService;
import com.liapkalo.profitsoft.filmwebapp.service.CsvService;
import com.liapkalo.profitsoft.filmwebapp.service.DirectorService;
import com.liapkalo.profitsoft.filmwebapp.service.FilmService;
import com.liapkalo.profitsoft.filmwebapp.utils.PaginationUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Year;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.liapkalo.profitsoft.filmwebapp.utils.JsonParseUtils.validateJson;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FilmServiceImpl implements FilmService {

    FilmRepository filmRepository;
    DirectorService directorService;
    ActorService actorService;
    ObjectMapper objectMapper;
    PaginationUtils paginationUtils;
    CsvService csvService;

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
        return filmRepository.save(buildFilm(filmDto));
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
     * @param filmNameAndGenreDto The data transfer object containing criteria for filtering films by name, genre, and director ID.
     * @param pageable            The pagination information specifying the page number, page size, sorting, etc.
     * @return A page containing films that match the provided criteria, or all films if no criteria are provided.
     * @throws NoSuchElementException if no films matching the criteria are found in the database.
     */
    @Override
    public Page<FilmNameAndGenreDto> getFilmsFromList(FilmNameAndGenreDto filmNameAndGenreDto, Pageable pageable) {
        log.info("Getting films from page: {}", pageable.getPageNumber());
        return Objects.nonNull(filmNameAndGenreDto) ?
                filmRepository.findAllByNameOrGenreOrDirector(filmNameAndGenreDto.getName(),
                filmNameAndGenreDto.getGenre(), filmNameAndGenreDto.getDirectorId(), pageable)
                        .orElseThrow(() -> new NoSuchElementException("No films found matching the provided criteria.")) :
                filmRepository.findAllFilmsNameAndGenreAndDirector(pageable)
                        .orElseThrow(() -> new NoSuchElementException("No films found in the database."));
    }

    /**
     * Generates a CSV report of films based on the provided criteria and writes it to the HttpServletResponse.
     * This method generates a CSV report containing the names and genres of films that match the provided criteria.
     * The report is written to the HttpServletResponse object as an attachment with the filename "films-report.csv".
     *
     * @param response              The HttpServletResponse object to which the CSV report will be written.
     * @param filmNameAndGenreDto   The data transfer object containing criteria for filtering films by name, genre, and director ID.
     * IOException          If an I/O error occurs while writing the CSV report to the HttpServletResponse.
     */
    @Override
    public void generateFilmReport(HttpServletResponse response, FilmNameAndGenreDto filmNameAndGenreDto) {
        log.info("Generating film report");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=films-report.csv");

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write("Name,Genre\n".getBytes());

            createFilmCsv(filmNameAndGenreDto, outputStream);

            outputStream.flush();
            log.info("Film report generated successfully!");
        } catch (IOException e) {
            log.error("Fail generate film report: {}", e.getMessage());
        }
    }

    /**
     * Parses a JSON file containing film data and saves the films to the database.
     * This method reads the content of the provided JSON file using a JsonParser and saves each film to the database.
     * The JSON file is expected to contain an array of film objects.
     *
     * @param file The JSON file containing film data.
     * IOException If an I/O error occurs while parsing the JSON file.
     */
    @Override
    public String getFilmsFromJson(MultipartFile file)  {
        log.info("Parsing file: {}", file.getOriginalFilename());
        int successesPars = 0;
        int failPars = 0;

        try (InputStream inputStream = file.getInputStream();
             JsonParser parser = objectMapper.getFactory().createParser(inputStream)) {

            validateJson(parser);

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                FilmDto filmDto = parser.readValueAs(FilmDto.class);
                if (checkToValid(filmDto)) {
                    filmRepository.save(buildFilm(filmDto));
                    successesPars++;
                } else failPars++;
            }

            log.info("File parsed successfully: {}", file.getOriginalFilename());

        } catch (IOException e) {
            log.error("Error parsing file: {}", file.getOriginalFilename(), e);
        }
        return "Successes imports: " + successesPars + "\nFails imports: " + failPars;
    }

    private boolean checkToValid(FilmDto filmDto) {
        return !filmDto.getName().isEmpty() &&
               !filmDto.getGenre().isEmpty() &&
               filmDto.getReleaseYear() < Year.now().getValue() &&
               !filmDto.getMainActors().isEmpty() &&
               !filmDto.getDirector().getName().isEmpty() &&
               filmDto.getDirector().getAge() > 0;
    }


    /**
     * Creates a CSV file containing information about films based on the provided criteria.
     * This method retrieves films from the database that match the provided name, genre, or director ID.
     * If the provided filmNameAndGenreDto is not null, it filters films based on the criteria provided in the object.
     * If the filmNameAndGenreDto is null, it retrieves all films.
     * The films' information is written to the provided outputStream in CSV format.
     * Pagination is applied to handle large datasets.
     *
     * @param filmNameAndGenreDto The data transfer object containing criteria for filtering films by name, genre, and director ID.
     * @param outputStream         The OutputStream to which the CSV data will be written.
     * @throws IOException         If an I/O error occurs while writing the CSV data.
     */
    @Override
    public void createFilmCsv(FilmNameAndGenreDto filmNameAndGenreDto, OutputStream outputStream) throws IOException {
        Page<FilmNameAndGenreDto> filmsPage;
        int pageNumber = paginationUtils.PAGE_NUMBER;
        int pageSize = paginationUtils.PAGE_SIZE;
        do {
            filmsPage = getFilmsFromList(filmNameAndGenreDto, PageRequest.of(pageNumber, pageSize));
            pageNumber = csvService.createCsvLine(outputStream, pageNumber, filmsPage);
        } while (!filmsPage.isEmpty());
    }

    Film buildFilm(FilmDto request) {

        List<Actor> actors = request.getMainActors()
                .stream()
                .map(a -> actorService.createActor(new ActorDto(a.getName()))).toList();

        return Film.builder()
                .name(request.getName())
                .genre(request.getGenre())
                .releaseYear(request.getReleaseYear())
                .mainActors(actors)
                .director(
                        directorService.createDirector(
                                new DirectorDto(
                                        request.getDirector().getName(),
                                        request.getDirector().getAge())))
                .build();
    }

    /** (TEMPORARY METHOD - UNTIL APP WITHOUT UI, TO HAVE ABILITY UPDATE ONE FILED) */
    private void updateFilmFields(Film film, FilmDto filmDto) {
        if (Objects.nonNull(filmDto.getName())) {
            film.setName(filmDto.getName());
        }
        if (Objects.nonNull(filmDto.getGenre())) {
            film.setGenre(filmDto.getGenre());
        }
        if (Objects.nonNull(filmDto.getReleaseYear()) &&
                Year.now().isAfter(Year.of(filmDto.getReleaseYear()))) {
            film.setReleaseYear(filmDto.getReleaseYear());
        }
        updateMainActors(film, filmDto.getMainActors());
        updateDirector(film, filmDto.getDirector());
    }

    private void updateMainActors(Film film, List<Actor> actors) {
        if (Objects.nonNull(actors)) {
            film.setMainActors(actors.stream()
                    .map(actorDto -> actorService.createActor(new ActorDto(actorDto.getName())))
                    .collect(Collectors.toList()));
        }
    }

    private void updateDirector(Film film, Director director) {
        if (Objects.nonNull(director)) {
            film.setDirector(directorService.createDirector(new DirectorDto(director.getName(), director.getAge())));
        }
    }


}
