package com.grimaldi.gestao_de_pacientes.service.validation.impl;

import com.grimaldi.gestao_de_pacientes.dto.UpdateScheduleRequest;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.exception.PastDateAndTimeException;
import com.grimaldi.gestao_de_pacientes.service.validation.UpdateScheduleValidation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ValidatePastDateAndTimeUpdateImpl implements UpdateScheduleValidation {

    @Override
    public void validate(UpdateScheduleRequest request, Schedule currentSchedule) {

        LocalDate finalDate =
                request.date() != null
                        ? request.date()
                        : currentSchedule.getDate();

        LocalTime finalTime =
                request.time() != null
                        ? request.time()
                        : currentSchedule.getTime();

        if (finalDate.isBefore(LocalDate.now())) {
            throw new PastDateAndTimeException("Data inválida");
        }

        if (finalDate.isEqual(LocalDate.now())
                && finalTime != null
                && finalTime.isBefore(LocalTime.now())) {
            throw new PastDateAndTimeException("Hora inválida");
        }
    }
}
