package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.Validation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final List<Validation> validations;

    public ScheduleService(ScheduleRepository scheduleRepository, List<Validation> validations) {
        this.scheduleRepository = scheduleRepository;
        this.validations = validations;
    }

    public Schedule addSchedule(Schedule schedule) {
        validations.forEach(v -> v.validate(schedule));
        schedule.setAvailable(true);
        return scheduleRepository.save(schedule);
    }
}
