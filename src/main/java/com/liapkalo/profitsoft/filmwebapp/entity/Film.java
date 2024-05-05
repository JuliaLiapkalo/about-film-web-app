package com.liapkalo.profitsoft.filmwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicUpdate
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "films", indexes = {@Index(name = "idx_name", columnList = "name"),
                                  @Index(name = "idx_actors", columnList = "mainActors"),
                                  @Index(name = "idx_name_genre_director", columnList = "name, genre, director")}
)
public class Film {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     @Column(name = "name")
     String name;

     @Column(name = "genre")
     String genre;

     @Column(name = "release_year")
     int releaseYear;

     @ManyToMany(fetch = FetchType.EAGER)
     @JoinTable(
             name = "film_actor",
             joinColumns = @JoinColumn(name = "film_id"),
             inverseJoinColumns = @JoinColumn(name = "actor_id"))
     List<Actor> mainActors;

     @ManyToOne(fetch = FetchType.EAGER)
     Director director;



}
