package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AppointmentRequest(@NotNull UUID patientId, @NotNull String title, @NotBlank LocalDate date) {
}
