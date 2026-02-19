package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientRequest(@NotBlank String name,@NotBlank String cpf,@NotBlank String phone,@NotNull Integer age) {
}
