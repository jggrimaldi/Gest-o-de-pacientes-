package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleRequest;
import com.grimaldi.gestao_de_pacientes.dto.ScheduleResponse;
import com.grimaldi.gestao_de_pacientes.dto.UpdateAvailableRequest;
import com.grimaldi.gestao_de_pacientes.dto.UpdateScheduleRequest;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.exception.IdNotExistException;
import com.grimaldi.gestao_de_pacientes.exception.PastDateAndTimeException;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.IdValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateScheduleValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.UpdateAvailableValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.UpdateScheduleValidation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final List<CreateScheduleValidation> createScheduleValidations;
    private final List<IdValidation> idValidations;
    private final List<UpdateScheduleValidation> updateScheduleValidations;
    private final List<UpdateAvailableValidation> updateAvailableValidations;

    public ScheduleService(ScheduleRepository scheduleRepository, List<CreateScheduleValidation> createScheduleValidations, List<IdValidation> idValidations, List<UpdateScheduleValidation> updateScheduleValidations, List<UpdateAvailableRequest> updateAvailableRequests, List<UpdateAvailableValidation> updateAvailableValidations) {
        this.scheduleRepository = scheduleRepository;
        this.createScheduleValidations = createScheduleValidations;
        this.idValidations = idValidations;
        this.updateScheduleValidations = updateScheduleValidations;
        this.updateAvailableValidations = updateAvailableValidations;
    }

    @Transactional
    public Schedule addSchedule(ScheduleRequest request) {
        //Percorre a lista de validações validando tudo
        createScheduleValidations.forEach(v -> v.validate(request));

        //transforma o DTO em na entidade
        Schedule schedule = new Schedule(null, request.date(), request.time(), true);

        return scheduleRepository.save(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAll() {
        //Busca no banco
        List<Schedule> schedules = scheduleRepository.findAll();

        //transforma a entidade em novos objetos Response e devolve para lista
        return schedules.stream()
                .map(ScheduleResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public Optional<Schedule> findById(UUID id) {
        return scheduleRepository.findById(id);
    }

    @Transactional
    public ScheduleResponse partialUpdate(UUID id, UpdateScheduleRequest newSchedule) {

        Schedule oldSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        // chama TODAS as validações de update
        updateScheduleValidations.forEach(v ->
                v.validate(newSchedule, oldSchedule)
        );

        //Se não passar nada fica com o antigo
        LocalDate finalDate =
                newSchedule.date() != null
                        ? newSchedule.date()
                        : oldSchedule.getDate();

        //Se não passar nada fica com o antigo
        LocalTime finalTime =
                newSchedule.time() != null
                        ? newSchedule.time()
                        : oldSchedule.getTime();

        oldSchedule.setDate(finalDate);
        oldSchedule.setTime(finalTime);

        return new ScheduleResponse(scheduleRepository.save(oldSchedule));
    }

    @Transactional
    public ScheduleResponse updateAvailable(UUID id, UpdateAvailableRequest request) {
        Schedule oldschedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        updateAvailableValidations.forEach(v -> v.validate(request));

        oldschedule.setAvailable(request.available());

        return new ScheduleResponse(scheduleRepository.save(oldschedule));
    }

    //deletar horario
    @Transactional
    public void delete(UUID id) {
        idValidations.forEach(v -> v.validate(id));
        scheduleRepository.deleteById(id);
    }
}
