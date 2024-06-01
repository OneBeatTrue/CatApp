package ru.onebeattrue.dto;

import ru.onebeattrue.enums.Color;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public record ColorAndIdDTO(
    @NotNull Color color,
    @Min(1) Long masterId
) implements Serializable {}