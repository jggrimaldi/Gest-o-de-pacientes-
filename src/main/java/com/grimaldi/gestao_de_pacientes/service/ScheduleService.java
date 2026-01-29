package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleResponse;
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
        //Percorre a lista de validações validando tudo
        validations.forEach(v -> v.validate(schedule));

        //ao criar fica disponivel
        schedule.setAvailable(true);
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleResponse> findAll() {
        //Busca no banco
        List<Schedule> schedules = scheduleRepository.findAll();

        //transforma a entidade em novos objetos Response e devolve para lista
        return schedules.stream()
                .map(ScheduleResponse::new)
                .toList();
    }

    public List<ScheduleResponse> findAvailable() {

        List<Schedule> schedules = scheduleRepository.findAll();

        //esteira de dados
        return schedules.stream()
                //filtro true
                .filter(schedule -> schedule.isAvailable())
                //Para cada Schedule que passou no filtro ➡ cria um ScheduleResponse
                .map(ScheduleResponse::new)
                .toList();
    }
}
