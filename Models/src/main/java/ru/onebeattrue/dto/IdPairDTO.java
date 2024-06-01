package ru.onebeattrue.dto;

import javax.validation.constraints.Min;
import java.io.Serializable;

public record IdPairDTO(
        @Min(1) Long firstId,
        @Min(1) Long secondId
) implements Serializable {}
