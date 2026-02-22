package com.grimaldi.gestao_de_pacientes.dto;

import jakarta.validation.constraints.NotBlank;

public record AppointmentNoteUpdateRequest(@NotBlank String notes, @NotBlank String imageUrl) {
}
