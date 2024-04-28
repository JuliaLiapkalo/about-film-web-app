package com.liapkalo.profitsoft.filmwebapp.repository;

import com.liapkalo.profitsoft.filmwebapp.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findByName(String name);
}
