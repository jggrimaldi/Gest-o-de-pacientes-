package com.grimaldi.gestao_de_pacientes.dto;

import java.time.LocalDate;

public record AppointmentUpdateRequest(String title, LocalDate date) {
}
