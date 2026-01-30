package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleRequest;
import com.grimaldi.gestao_de_pacientes.dto.ScheduleResponse;
import com.grimaldi.gestao_de_pacientes.dto.UpdateScheduleRequest;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.exception.IdNotExistException;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.IdValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateScheduleValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.UpdateScheduleValidation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final List<CreateScheduleValidation> createScheduleValidations;
    private final List<IdValidation> idValidations;
    private final List<UpdateScheduleValidation> updateScheduleValidations;

    public ScheduleService(ScheduleRepository scheduleRepository, List<CreateScheduleValidation> createScheduleValidations, List<IdValidation> idValidations, List<UpdateScheduleValidation> updateScheduleValidations) {
        this.scheduleRepository = scheduleRepository;
        this.createScheduleValidations = createScheduleValidations;
        this.idValidations = idValidations;
        this.updateScheduleValidations = updateScheduleValidations;
    }

    public Schedule addSchedule(ScheduleRequest request) {
        //Percorre a lista de validações validando tudo
        createScheduleValidations.forEach(v -> v.validate(request));

        //transforma o DTO em na entidade
        Schedule schedule = new Schedule(null, request.date(), request.time(), true);

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
    //Optional
    public Optional<Schedule> findById(UUID id) {
        return scheduleRepository.findById(id);
    }

    public ScheduleResponse update(UUID id, UpdateScheduleRequest newSchedule) {
        Schedule oldSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        updateScheduleValidations.forEach(v -> v.validate(newSchedule));

        if(newSchedule.date() != null) {
            oldSchedule.setDate(newSchedule.date());
        }
        if (newSchedule.time() != null) {
            oldSchedule.setTime(newSchedule.time());
        }

        return new ScheduleResponse(scheduleRepository.save(oldSchedule));
    }

    public ScheduleResponse updateAvailable(UUID id, UpdateScheduleRequest request) {
        Schedule oldschedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        updateScheduleValidations.forEach(v -> v.validate(request));

        oldschedule.setAvailable(request.available());

        return new ScheduleResponse(scheduleRepository.save(oldschedule));
    }

    //deletar horario
    public void delete(UUID id) {
        idValidations.forEach(v -> v.validate(id));
        scheduleRepository.deleteById(id);
    }
}
