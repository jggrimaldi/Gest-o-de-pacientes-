package com.grimaldi.gestao_de_pacientes.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleReponse(LocalDate date, LocalTime time) {
}
