package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.exception.ScheduleUnavailableException;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateAppointmentValidation;

public class ValidateScheduleAvailableImpl implements CreateAppointmentValidation {
    @Override
    public void validate(Schedule schedule) {
        if(schedule.isAvailable() == true) {
            throw new ScheduleUnavailableException("Horário Indisponível");
        }
    }
}
