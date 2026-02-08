package com.grimaldi.gestao_de_pacientes.dto;

import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentResponse(UUID id, LocalDate date, LocalTime time, AppointmentStatus status, UUID ScheduleId) {
}
