package com.grimaldi.gestao_de_pacientes.repository;

import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
}
