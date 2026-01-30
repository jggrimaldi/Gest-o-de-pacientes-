package com.grimaldi.gestao_de_pacientes.service.validation;

import com.grimaldi.gestao_de_pacientes.dto.UpdateScheduleRequest;

public interface UpdateScheduleValidation{

    void validate(UpdateScheduleRequest request);
}
