package com.grimaldi.gestao_de_pacientes.dto;

import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(
        UUID id,
        LocalDate date,
        String title,
        String notes,
        String imageUrl,
        AppointmentStatus status,
        String patientName,
        String patientPhone,
        LocalDateTime updatedAt // Importante para a dentista saber quando a nota foi escrita
) {
    // Construtor compacto para transformar a Entity em DTO
    public AppointmentResponse(Appointment appointment) {
        this(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTitle(),
                appointment.getNotes(),
                appointment.getImageUrl(),
                appointment.getStatus(),
                appointment.getPatient().getName(),
                appointment.getPatient().getPhone(),
                appointment.getUpdatedAt()
        );
    }
}
