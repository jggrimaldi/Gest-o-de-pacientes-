package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleRequest;
import com.grimaldi.gestao_de_pacientes.exception.PastDateAndTimeException;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateScheduleValidation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidatePastDateAndTimeCreateImpl implements CreateScheduleValidation {


    @Override
    public void validate(ScheduleRequest request) {

        //data não nula
        if(request.date() != null) {

            //data no passado
            if (request.date().isBefore(LocalDate.now())) {
                throw new PastDateAndTimeException("Data inválida");

            }
            //data hj, com tempo no passado
            if (request.date().isEqual(LocalDate.now())
                    && request.time() != null
                    && request.time().isBefore(LocalTime.now())) {
                throw new PastDateAndTimeException("Hora inválida");

            }
        }
    }
}
