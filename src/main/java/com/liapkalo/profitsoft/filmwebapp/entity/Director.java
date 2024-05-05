package com.liapkalo.profitsoft.filmwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "directors", indexes = {@Index(name = "idx_director_name", columnList = "name")})
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "age")
    int age;

}
