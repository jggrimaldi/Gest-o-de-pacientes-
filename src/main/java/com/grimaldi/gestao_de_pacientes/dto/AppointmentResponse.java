package com.grimaldi.gestao_de_pacientes.dto;

import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AppointmentResponse(UUID id, LocalDate date, LocalTime time, AppointmentStatus status, UUID ScheduleId, String patientName, String patientPhone) {

    public AppointmentResponse(Appointment appointment){
        this(
                appointment.getId(),
                appointment.getSchedule().getDate(),
                appointment.getSchedule().getTime(),
                appointment.getStatus(),
                appointment.getSchedule().getId(),
                appointment.getPatient().getName(),
                appointment.getPatient().getPhone());
    }

}
