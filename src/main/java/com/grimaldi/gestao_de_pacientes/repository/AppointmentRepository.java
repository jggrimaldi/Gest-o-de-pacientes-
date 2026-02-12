package com.grimaldi.gestao_de_pacientes.repository;

import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    //para ordenar automaticamente por horário
    List<Appointment> findBySchedule_DateBetweenAndStatusInOrderByScheduleDateAscScheduleTimeAsc(
            LocalDate startDate,
            LocalDate endDate,
            List<AppointmentStatus> status
    );

    boolean existsByPatientIdAndScheduleId(UUID patientId, UUID scheduleId);
    }

