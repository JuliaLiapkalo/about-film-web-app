package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DirectorDto {

    @NotBlank
    @NotNull
    @Size(min = 1, max = 255)
    String name;

    @NotNull
    @Min(1)
    @Max(120)
    Integer age;

}
