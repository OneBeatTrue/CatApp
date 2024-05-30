package ru.onebeattrue.dto;
import ru.onebeattrue.entities.Cat;
import ru.onebeattrue.models.Color;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

public record CatDTO(
        @Min(0) Long id,
        @Size(min = 2, max = 20)
        @NotBlank(message = "Name is required") String name,
        @Past(message = "Birth date should be in the past") Date birthDate,
        @NotBlank(message = "Breed is required") String breed,
        @NotNull(message = "Color is required. Use MIXED instead") Color color,
        @NotNull @Min(0) Long master
) {
    public CatDTO(Cat cat) {
        this(cat.getId(), cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor(), cat.getMaster().getId());
    }

    public CatDTO(String name, Date birthDate, String breed, String color, Long master) {
        this(0L, name, birthDate, breed, Color.valueOf(color), master);
    }

    public CatDTO(Long id, String name, Date birthDate, String breed, String color, Long master) {
        this(id, name, birthDate, breed, Color.valueOf(color), master);
    }
}
