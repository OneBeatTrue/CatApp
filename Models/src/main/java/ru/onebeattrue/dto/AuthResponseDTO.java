package ru.onebeattrue.dto;

import java.io.Serializable;

public record AuthResponseDTO (
        String token
) implements Serializable {}
