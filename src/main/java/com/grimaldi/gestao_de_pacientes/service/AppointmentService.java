package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.AppointmentNoteUpdateRequest;
import com.grimaldi.gestao_de_pacientes.dto.AppointmentRequest;
import com.grimaldi.gestao_de_pacientes.dto.AppointmentResponse;
import com.grimaldi.gestao_de_pacientes.dto.AppointmentUpdateRequest;
import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.entity.Patient;
import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;
import com.grimaldi.gestao_de_pacientes.exception.IdNotExistException;
import com.grimaldi.gestao_de_pacientes.repository.AppointmentRepository;
import com.grimaldi.gestao_de_pacientes.repository.PatientRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.CreateAppointmentValidation;
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
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(List<CreateAppointmentValidation> createAppointmentValidations, List<StatusPendingValidation> statusPendingValidations, AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.createAppointmentValidations = createAppointmentValidations;
        this.statusPendingValidations = statusPendingValidations;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Appointment creatAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        createAppointmentValidations.forEach(v -> v.validate(request));


        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPatient(patient);
        appointment.setDate(request.date());
        appointment.setTitle(request.title());

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
    public List<AppointmentResponse> findByPatient(UUID patientId) {
        // Valida se o paciente existe antes de buscar
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        return appointmentRepository.findByPatientIdOrderByDateDesc(patientId)
                .stream()
                .map(AppointmentResponse::new)
                .toList();
    }

    @Transactional
    public  AppointmentResponse updateNotePad(UUID appointmentId, AppointmentNoteUpdateRequest updateRequest){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        //Verifica se foi recebido algo no DTO
        if (updateRequest.notes() != null) {
            appointment.setNotes(updateRequest.notes());
        }
        if (updateRequest.imageUrl() != null) {
            appointment.setImageUrl(updateRequest.imageUrl());
        }

        return new AppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponse updateDetails(UUID appointmentId,AppointmentUpdateRequest updateRequest) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        //Verifica se foi recebido algo no DTO
        if (updateRequest.title() != null) {
            appointment.setTitle(updateRequest.title());
        }
        if (updateRequest.date() != null) {
            appointment.setDate(updateRequest.date());
        }

        return new AppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAgenda(LocalDate startDate, LocalDate endDate) {
        List<AppointmentStatus> validStatus = List.of(AppointmentStatus.PENDING, AppointmentStatus.FINISHED);

        List<Appointment> agenda = appointmentRepository.findByDateBetweenAndStatusInOrderByDateAsc(startDate, endDate, validStatus);

        return agenda.stream()
                .map(AppointmentResponse::new)
                .toList();
    }

    @Transactional
    public AppointmentResponse finishAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        statusPendingValidations.forEach(v -> v.validate(appointment));

        appointment.setStatus(AppointmentStatus.FINISHED);
        return new AppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentResponse cancelAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        statusPendingValidations.forEach(v -> v.validate(appointment));

        appointment.setStatus(AppointmentStatus.CANCELED);
        return new AppointmentResponse(appointmentRepository.save(appointment));
    }

    @Transactional
    public void delete(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IdNotExistException("Id não encontrado"));

        appointmentRepository.delete(appointment);
    }
}
