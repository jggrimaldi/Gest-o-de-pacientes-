package com.grimaldi.gestao_de_pacientes.service.validation;

import com.grimaldi.gestao_de_pacientes.dto.UpdateScheduleRequest;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;

public interface UpdateScheduleValidation{

    void validate(UpdateScheduleRequest request, Schedule currentSchedule);}
