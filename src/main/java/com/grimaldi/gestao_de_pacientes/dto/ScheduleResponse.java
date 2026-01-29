package com.grimaldi.gestao_de_pacientes.dto;

import com.grimaldi.gestao_de_pacientes.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleResponse(
        UUID id,
        LocalDate date,
        LocalTime time,
        boolean available
) {
    public ScheduleResponse(Schedule s) {
        this(s.getId(), s.getDate(), s.getTime(), s.isAvailable());
    }
}
