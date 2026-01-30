package com.grimaldi.gestao_de_pacientes.exception;

public class PastDateAndTimeException extends RuntimeException{

    public PastDateAndTimeException(String message) {
        super(message);
    }
}
