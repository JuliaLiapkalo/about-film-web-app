package utils;


import com.liapkalo.profitsoft.filmwebapp.entity.Director;
import com.liapkalo.profitsoft.filmwebapp.entity.dto.DirectorDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DirectorUtils {

   public static Director buildDirector() {
      return Director.builder()
              .id(1L)
               .name("Director")
               .age(40)
               .build();
   }

   public static DirectorDto buildDirectorDto() {
      return DirectorDto.builder()
              .name("Updated director")
              .age(45)
              .build();
   }

   public static DirectorDto buildDirectorDtoInvalidName() {
      return DirectorDto.builder()
              .age(45)
              .build();
   }

   public static DirectorDto buildDirectorDtoInvalidAge() {
      return DirectorDto.builder()
              .name("Updated director")
              .build();
   }

   public static DirectorDto buildDirectorDtoInvalid() {
      return DirectorDto.builder()
              .name("")
              .age(0)
              .build();
   }
}