package ru.onebeattrue.dto;

import lombok.Data;
import ru.onebeattrue.models.Color;

import javax.validation.constraints.NotNull;

public class ColorDTO {
    private final Color color;
    public ColorDTO(String color) {
        this.color = Color.valueOf(color);
    }

    public Color color() {
        return this.color;
    }
}
