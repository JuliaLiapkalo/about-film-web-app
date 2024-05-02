package com.liapkalo.profitsoft.filmwebapp.specification;

import com.liapkalo.profitsoft.filmwebapp.entity.Film;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.FilmFilterDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class FilmSpecifications {

    public static Specification<Film> filterFilm(FilmFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            Specification<Film> specification = Specification.where(null);

            if (!StringUtils.isEmpty(filter.getName())) {
                specification = specification.and(hasName(filter.getName()));
            }

            if (!StringUtils.isEmpty(filter.getGenre())) {
                specification = specification.and(hasGenre(filter.getGenre()));
            }

            return specification.toPredicate(root, query, criteriaBuilder);
        };
    }

    private static Specification<Film> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    private static Specification<Film> hasGenre(String genre) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genre"), genre);
    }
}
