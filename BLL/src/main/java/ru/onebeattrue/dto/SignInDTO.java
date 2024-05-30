package ru.onebeattrue.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record SignInDTO(
        @Size(min = 2, max = 20)
        @NotBlank(message = "Username is required")
        String username,
        @Size(min = 8, max = 30)
        @NotBlank(message = "Password is required")
        String password
) {}
