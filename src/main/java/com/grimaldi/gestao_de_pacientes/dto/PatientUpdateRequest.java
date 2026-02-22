package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientUpdateRequest(
        @NotBlank String name,
        @NotBlank String phone,
        @NotNull Integer age) {
}
