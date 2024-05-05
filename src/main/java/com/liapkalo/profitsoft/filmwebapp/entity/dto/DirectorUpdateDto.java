package com.liapkalo.profitsoft.filmwebapp.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DirectorUpdateDto {

    @Size(min = 1, max = 255)
    String name;

    @Min(1)
    @Max(120)
    Integer age;

}
