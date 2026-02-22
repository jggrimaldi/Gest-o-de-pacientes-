package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.node.StringNode;

public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {
}
