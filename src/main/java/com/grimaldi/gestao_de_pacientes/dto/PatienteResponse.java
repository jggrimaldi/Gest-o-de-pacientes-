package com.grimaldi.gestao_de_pacientes.dto;

import com.grimaldi.gestao_de_pacientes.entity.Patient;

import java.util.UUID;

public record PatienteResponse(
        UUID id,
        String name,
        String cpf,
        String phone,
        Integer age,
        String note,
        String imageUrl
    ) {
    public PatienteResponse(Patient patient) {
        this(
                patient.getId(),
                patient.getName(),
                patient.getCpf(),
                patient.getPhone(),
                patient.getAge(),
                patient.getNotes(),
                patient.getImageUrl()
        );
    }
}
