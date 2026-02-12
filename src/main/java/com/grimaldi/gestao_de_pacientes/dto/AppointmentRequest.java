package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AppointmentRequest(@NotNull UUID scheduleId,@NotNull UUID patientId) {
}
