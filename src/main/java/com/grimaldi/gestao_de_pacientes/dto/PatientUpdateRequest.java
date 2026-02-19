package com.grimaldi.gestao_de_pacientes.dto;

public record PatientUpdateRequest(
        String name,
        String phone,
        Integer age,
        String notes,
        String imageUrl) {
}
