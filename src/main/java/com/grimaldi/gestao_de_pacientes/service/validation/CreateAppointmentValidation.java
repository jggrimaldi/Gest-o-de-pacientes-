package com.grimaldi.gestao_de_pacientes.service.validation;

import com.grimaldi.gestao_de_pacientes.entity.Patient;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;

public interface CreateAppointmentValidation {

    void validate(Schedule schedule, Patient patient);
}
