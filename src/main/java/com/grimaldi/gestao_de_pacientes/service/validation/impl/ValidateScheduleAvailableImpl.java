package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.entity.Patient;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.exception.PatientDuplicateAppointmentException;
import com.grimaldi.gestao_de_pacientes.exception.ScheduleUnavailableException;
import com.grimaldi.gestao_de_pacientes.repository.AppointmentRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateAppointmentValidation;
import org.springframework.stereotype.Component;

@Component
public class ValidateScheduleAvailableImpl implements CreateAppointmentValidation {

    private final AppointmentRepository appointmentRepository;

    public ValidateScheduleAvailableImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void validate(Schedule schedule, Patient patient) {
        if(!schedule.isAvailable()) {
            throw new ScheduleUnavailableException("Horário Indisponível");
        }

        boolean alreadyBooked = appointmentRepository.existsByPatientIdAndScheduleId(patient.getId(), schedule.getId());

        if(alreadyBooked) {
            throw new PatientDuplicateAppointmentException("Você já possui uma reserva para este horário");
        }
    }


}
