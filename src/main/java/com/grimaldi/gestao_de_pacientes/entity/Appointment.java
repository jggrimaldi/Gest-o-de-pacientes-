package com.grimaldi.gestao_de_pacientes.entity;

import com.grimaldi.gestao_de_pacientes.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Appointment {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID pacientId;

    @OneToOne
    @JoinColumn(name = "schedule_id", unique = true)
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    //Auditoria, o spring faz automatico
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;




}
