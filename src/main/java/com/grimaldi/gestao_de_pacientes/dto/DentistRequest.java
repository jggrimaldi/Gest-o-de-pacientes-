package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DentistRequest(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password, String cro) {
}
