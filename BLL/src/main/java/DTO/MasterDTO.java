package DTO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public record MasterDTO(
        Long id,
        String name,
        Date birthDate
) {};
