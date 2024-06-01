package ru.onebeattrue.dto;

import ru.onebeattrue.entities.master.Master;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public record MasterDTO(
        @Min(0) Long id,
        @Size(min = 2, max = 20)
        @NotBlank(message = "Name is required") String name,
        @Past(message = "Birth date should be in the past") Date birthDate
) implements Serializable {
    public MasterDTO(Master master) {
        this(master.getId(), master.getName(), master.getBirthDate());
    }

    public MasterDTO(String name, Date birthDate) {
        this(0L, name, birthDate);
    }
};
