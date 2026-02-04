package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.dto.UpdateAvailableRequest;
import com.grimaldi.gestao_de_pacientes.exception.AvailableNullException;
import com.grimaldi.gestao_de_pacientes.service.validation.UpdateAvailableValidation;
import org.springframework.stereotype.Component;

@Component
public class ValidateAvailableNullImpl implements UpdateAvailableValidation {

    @Override
    public void validate(UpdateAvailableRequest request) {
        if(request.available() == null) {
            throw new AvailableNullException("Disponibilidade não pode ser nula");
        }
    }
}
