package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {

    @NotBlank
    @Size(min = 1, max = 255)
    String name;

    @Min(1)
    int age;
}
