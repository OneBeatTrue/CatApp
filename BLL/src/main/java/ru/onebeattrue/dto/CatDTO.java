package ru.onebeattrue.dto;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

public record CatDTO(
        @Min(0) Long id,
        @NotBlank(message = "Name is required") String name,
        @Past(message = "Birth date should be in the past") Date birthDate,
        @NotBlank(message = "Breed is required") String breed,
        @NotBlank(message = "Color is required") String color,
        @NotNull Long master
) {}
