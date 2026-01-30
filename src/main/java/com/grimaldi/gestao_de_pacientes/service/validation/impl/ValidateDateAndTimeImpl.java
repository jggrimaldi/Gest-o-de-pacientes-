package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleRequest;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateScheduleValidation;
import org.springframework.stereotype.Component;

@Component
public class ValidateDateAndTimeImpl implements CreateScheduleValidation {

    private final ScheduleRepository scheduleRepository;

    public ValidateDateAndTimeImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void validate(ScheduleRequest request) {
        boolean exists = scheduleRepository
                .existsByDateAndTime(request.date(), request.time());

        if (exists) {
            throw new RuntimeException("Horario já está ocupado");
        }
    }
}
