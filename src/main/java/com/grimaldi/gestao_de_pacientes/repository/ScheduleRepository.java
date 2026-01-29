package com.grimaldi.gestao_de_pacientes.repository;

import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    boolean existsByDateAndTime(LocalDate date, LocalTime time);
}
