package ru.onebeattrue.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public record MasterListDTO (
    List<MasterDTO> masters
) implements Serializable {}

