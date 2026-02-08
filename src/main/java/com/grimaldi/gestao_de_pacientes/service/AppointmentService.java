package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;
import com.grimaldi.gestao_de_pacientes.exception.IdNotExistException;
import com.grimaldi.gestao_de_pacientes.repository.AppointmentRepository;
import com.grimaldi.gestao_de_pacientes.repository.ScheduleRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateAppointmentValidation;
import com.grimaldi.gestao_de_pacientes.service.validation.IdValidation;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public class AppointmentService {

    private final List<CreateAppointmentValidation> createAppointmentValidations;
    private final List<IdValidation> idValidations;
    private AppointmentRepository appointmentRepository;
    private ScheduleRepository scheduleRepository;

    public AppointmentService(List<CreateAppointmentValidation> createAppointmentValidations, List<IdValidation> idValidations) {
        this.createAppointmentValidations = createAppointmentValidations;
        this.idValidations = idValidations;
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
}
