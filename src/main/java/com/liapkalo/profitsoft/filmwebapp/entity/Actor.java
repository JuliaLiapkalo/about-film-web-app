package com.liapkalo.profitsoft.filmwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "actors", indexes = {@Index(name = "idx_actor_name", columnList = "name")})
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;

    public Actor(String name) {
        this.name = name;
    }

}
