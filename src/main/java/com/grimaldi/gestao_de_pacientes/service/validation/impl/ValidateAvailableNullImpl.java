package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.dto.UpdateScheduleRequest;
import com.grimaldi.gestao_de_pacientes.exception.AvailableNullException;
import com.grimaldi.gestao_de_pacientes.service.validation.UpdateScheduleValidation;
import org.springframework.stereotype.Component;

@Component
public class ValidateAvailableNullImpl implements UpdateScheduleValidation {

    @Override
    public void validate(UpdateScheduleRequest request) {
        if(request.available() == null) {
            throw new AvailableNullException("Disponibilidade não pode ser nula");
        }
    }
}
