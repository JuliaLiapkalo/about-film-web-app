package com.liapkalo.profitsoft.filmwebapp.service.impl;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorUpdateDto;
import com.liapkalo.profitsoft.filmwebapp.entity.mapper.DirectorMapper;
import com.liapkalo.profitsoft.filmwebapp.repository.DirectorRepository;
import com.liapkalo.profitsoft.filmwebapp.service.DirectorService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static com.liapkalo.profitsoft.filmwebapp.utils.GetMethods.getSetterMethods;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DirectorServiceImpl implements DirectorService {

    DirectorRepository directorRepository;
    DirectorMapper directorMapper;

    /**
     * Creates a director based on the provided DirectorDto object.
     * This method first checks if a director with the same name and age already exists in the database.
     * If a director with the same name and age exists, it returns that director.
     * If not, it creates a new director using the provided DirectorDto and saves it to the database.
     *
     * @param directorDto The data transfer object containing information about the director to be created.
     * @return The created director if it already exists in the database, otherwise a new director saved to the database.
     */
    @Override
    public Director getOrCreateDirector(DirectorDto directorDto) {
        log.info("Creating director: {}", directorDto);

        Optional<Director> director = directorRepository.findByNameAndAge(directorDto.getName(), directorDto.getAge());
        return director.orElseGet(() -> directorRepository.save(directorMapper.toDirector(directorDto)));

    }

    /**
     * Retrieves a list of all directors from the database.
     * This method retrieves all directors stored in the database and returns them as a list.
     *
     * @return A list containing all directors stored in the database.
     */
    @Override
    public List<Director> getDirectors() {
        log.info("Getting all directors");

        return directorRepository.findAll();
    }

    /**
     * Updates the information of a director identified by the given ID using the provided DirectorDto object.
     * This method first retrieves the director from the database based on the provided ID.
     * If the director with the given ID is found, its information is updated with the values provided in the DirectorDto object,
     * and the updated director is saved back to the database.
     * If no director with the given ID is found, an IllegalArgumentException is thrown.
     *
     * @param id          The ID of the director to be updated.
     * @param directorDto The data transfer object containing the updated information for the director.
     * @return The updated director.
     * @throws IllegalArgumentException if no director with the given ID is found in the database.
     */
    @Transactional
    @Override
    public Director updateDirector(Long id, DirectorUpdateDto directorDto) {
        log.info("Updating director: {}", directorDto);

        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Director with id " + id + " not found"));

        return directorRepository.save(updateDirectorFields(director, directorDto));
    }

    /**
     * Deletes a director from the database based on the provided ID.
     *
     * @param id The ID of the director to be deleted.
     * @return A message indicating the successful deletion of the director.
     */
    @Transactional
    @Override
    public String deleteDirector(Long id) {
        log.info("Deleting director with id: {}", id);

        directorRepository.deleteById(id);

        return "Director with id [" + id + "] deleted successfully!";
    }

    private Director updateDirectorFields(Director director, DirectorUpdateDto directorDto) {
        Map<String, Method> setters = getSetterMethods(Director.class);
        Field[] fields = directorDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(directorDto);
                if (value != null) {
                    String fieldName = field.getName();
                    Method setter = setters.get(fieldName.toLowerCase());
                    if (setter != null) {
                        setter.invoke(director, value);
                    }
                }
            } catch (Exception e) {
               log.error(e.getMessage());
            }
        }
        return director;
    }


}
