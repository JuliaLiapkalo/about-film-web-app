package com.liapkalo.profitsoft.filmwebapp.repository;

import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findByNameAndAge(String name, int age);
}
