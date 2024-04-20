package ru.onebeattrue.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

public record MasterDTO(
        @Min(0) Long id,
        @NotBlank(message = "Name is required") String name,
        @Past(message = "Birth date should be in the past") Date birthDate
) {};
