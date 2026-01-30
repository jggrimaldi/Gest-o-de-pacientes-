package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.exception.IdNotExistException;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.IdValidation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ValidateIdImpl implements IdValidation {

    private final ScheduleRepository scheduleRepository;

    public ValidateIdImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void validate(UUID id) {
        boolean exist = scheduleRepository
                .existsById(id);

        if(!exist) {
            throw new IdNotExistException("Id não existe");
        }
    }
}
