package com.grimaldi.gestao_de_pacientes.dto;

import com.grimaldi.gestao_de_pacientes.entity.Dentist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record DentistResponse(UUID id,  String name,String email, String cro, String role) {
    public DentistResponse(Dentist dentist) {
        this(
                dentist.getId(),
                dentist.getName(),
                dentist.getEmail(),
                dentist.getCro(),
                dentist.getRole()
        );
    }
}
