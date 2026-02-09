package com.grimaldi.gestao_de_pacientes.service.validation;

import com.grimaldi.gestao_de_pacientes.entity.Appointment;

public interface StatusPendingValidation {

    public void validate(Appointment appointment);
}
