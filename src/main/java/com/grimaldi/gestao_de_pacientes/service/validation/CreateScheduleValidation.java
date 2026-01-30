package com.grimaldi.gestao_de_pacientes.service.validation;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleRequest;

public interface CreateScheduleValidation {

    void validate(ScheduleRequest request);
}
