package ru.onebeattrue.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public record SignUpDTO(
        @Size(min = 2, max = 20)
        @NotBlank(message = "Username is required")
        String username,
        @Size(min = 8, max = 30)
        @NotBlank(message = "Password is required")
        String password,
        @Size(min = 2, max = 20)
        @NotBlank(message = "Name is required") String name,
        @Past(message = "Birth date should be in the past") Date birthDate
) {}
