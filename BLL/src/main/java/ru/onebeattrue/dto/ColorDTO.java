package ru.onebeattrue.dto;

import ru.onebeattrue.models.Color;

import javax.validation.constraints.NotNull;

public record ColorDTO(@NotNull(message = "Color is required") Color color) {}
