package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.AppointmentResponse;
import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;
import com.grimaldi.gestao_de_pacientes.exception.IdNotExistException;
import com.grimaldi.gestao_de_pacientes.repository.AppointmentRepository;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateAppointmentValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.IdValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.StatusPendingValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private final List<CreateAppointmentValidation> createAppointmentValidations;
    private final List<StatusPendingValidation> statusPendingValidations;
    private final List<IdValidation> idValidations;
    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;

    public AppointmentService(List<CreateAppointmentValidation> createAppointmentValidations, List<StatusPendingValidation> statusPendingValidations, List<IdValidation> idValidations, AppointmentRepository appointmentRepository, ScheduleRepository scheduleRepository) {
        this.createAppointmentValidations = createAppointmentValidations;
        this.statusPendingValidations = statusPendingValidations;
        this.idValidations = idValidations;
        this.appointmentRepository = appointmentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Appointment creatAppointment(UUID scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        createAppointmentValidations.forEach(v -> v.validate(schedule));

        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setSchedule(schedule);
        schedule.setAvailable(false);

        scheduleRepository.save(schedule);

        return appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll() {
        //Busca no banco
        List<Appointment> appointments = appointmentRepository.findAll();

        //transforma a entidade em novos objetos Response e devolve para lista
        return appointments.stream()
                .map(AppointmentResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponse findById(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        return new AppointmentResponse(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAgenda(LocalDate startDate, LocalDate endDate) {
        List<AppointmentStatus> validStatus = List.of(AppointmentStatus.PENDING, AppointmentStatus.CONFIRMED);

        List<Appointment> agenda = appointmentRepository.findBySchedule_DateBetweenAndStatusIn(startDate, endDate, validStatus);

        return agenda.stream()
                .map(AppointmentResponse::new)
                .toList();
    }

    @Transactional
    public AppointmentResponse confirmAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        statusPendingValidations.forEach(v -> v.validate(appointment));

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return new AppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public void delete(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        appointment.getSchedule().setAvailable(true);
        scheduleRepository.save(appointment.getSchedule());
        appointmentRepository.delete(appointment);
    }
}
