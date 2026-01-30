package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleResponse;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.DeleteValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.ScheduleValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final List<ScheduleValidation> scheduleValidations;
    private final List<DeleteValidation> DeleteValidations;

    public ScheduleService(ScheduleRepository scheduleRepository, List<ScheduleValidation> scheduleValidations) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleValidations = scheduleValidations;
    }

    public Schedule addSchedule(Schedule schedule) {
        //Percorre a lista de validações validando tudo
        scheduleValidations.forEach(v -> v.validate(schedule));

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
                .filter(Schedule::isAvailable)
                //Para cada Schedule que passou no filtro ➡ cria um ScheduleResponse
                .map(ScheduleResponse::new)
                .toList();
    }

    //pode o não existir o id
    public Optional<Schedule> findById(UUID id) {
        return scheduleRepository.findById(id);
    }

    public void delete(UUID id) {
        DeleteValidations.forEach(v -> v.validate(id));
        scheduleRepository.deleteById(id);
    }
}
