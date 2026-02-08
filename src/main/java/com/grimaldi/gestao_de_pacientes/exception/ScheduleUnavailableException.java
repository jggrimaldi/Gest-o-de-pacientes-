package com.grimaldi.gestao_de_pacientes.exception;

public class ScheduleUnavailableException extends RuntimeException{

    public ScheduleUnavailableException(String menssage) {
        super(menssage);
    }
}
