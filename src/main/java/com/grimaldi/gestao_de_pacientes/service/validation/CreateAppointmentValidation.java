package com.grimaldi.gestao_de_pacientes.service.validation;

import com.grimaldi.gestao_de_pacientes.dto.AppointmentRequest;
import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.entity.Patient;

public interface CreateAppointmentValidation {

    void validate(AppointmentRequest request);
}
