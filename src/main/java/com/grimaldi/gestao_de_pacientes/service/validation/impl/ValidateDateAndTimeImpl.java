package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.Validation;
import org.springframework.stereotype.Component;

@Component
public class ValidateDateAndTimeImpl implements Validation {

    private final ScheduleRepository scheduleRepository;

    public ValidateDateAndTimeImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void validate(Schedule schedule) {
        boolean exists = scheduleRepository
                .existsByDateAndTime(schedule.getDate(), schedule.getTime());

        if (exists) {
            throw new RuntimeException("Horario já está ocupado");
        }
    }
}
