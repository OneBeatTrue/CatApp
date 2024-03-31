package DTO;

import java.util.Date;

public record CatDTO(
        Long id,
        String name,
        Date birthDate,
        String breed,
        String color,
        Long master
) {};
