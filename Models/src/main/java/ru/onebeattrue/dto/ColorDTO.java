package ru.onebeattrue.dto;

import ru.onebeattrue.enums.Color;

import java.io.Serializable;

public class ColorDTO implements Serializable {
    private final Color color;
    public ColorDTO(String color) {
        this.color = Color.valueOf(color);
    }
    public Color color() {
        return this.color;
    }
}
