package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AppointmentUpdateRequest(@NotBlank String title, @NotBlank LocalDate date) {
}
