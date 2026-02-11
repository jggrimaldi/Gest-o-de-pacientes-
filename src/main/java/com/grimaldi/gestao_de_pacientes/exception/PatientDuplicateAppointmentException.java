package com.grimaldi.gestao_de_pacientes.exception;

public class PatientDuplicateAppointmentException extends RuntimeException{

    public PatientDuplicateAppointmentException(String message) {
        super(message);
    }
}
