package com.grimaldi.gestao_de_pacientes.controller;

import com.grimaldi.gestao_de_pacientes.model.dto.AppointmentNoteUpdateRequest;
import com.grimaldi.gestao_de_pacientes.model.dto.AppointmentRequest;
import com.grimaldi.gestao_de_pacientes.model.dto.AppointmentResponse;
import com.grimaldi.gestao_de_pacientes.model.dto.AppointmentUpdateRequest;
import com.grimaldi.gestao_de_pacientes.model.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/consultas")
@Tag(name = "Consultas", description = "Operações relacionadas a consulta")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Criar consulta")
    @PostMapping
    public ResponseEntity<AppointmentResponse> CreatAppointment(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentService.creatAppointment(request);

        AppointmentResponse response = new AppointmentResponse(appointment);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar consutas")
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<AppointmentResponse>> getAgenda(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(appointmentService.getAgenda(startDate,endDate));
    }

    @Operation(summary = "Buscar consulta por Id")
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.findById(appointmentId));
    }

    @Operation(summary = "Adicionar nota no bloco de notas")
    @PatchMapping("/{appointmentId}/anotacao")
    public  ResponseEntity<AppointmentResponse> updateNotePad(@PathVariable UUID appointmentId , @RequestBody AppointmentNoteUpdateRequest updateRequest) {
        return ResponseEntity.ok(appointmentService.updateNotePad(appointmentId, updateRequest));
    }

    @Operation(summary = "Atualizar detalhes da consulta")
    @PatchMapping("/{appointmentId}/detalhes")
    public ResponseEntity<AppointmentResponse> updateDetails(
            @PathVariable UUID appointmentId, @RequestBody AppointmentUpdateRequest request) {
        return ResponseEntity.ok(appointmentService.updateDetails(appointmentId, request));
    }

    @Operation(summary = "Finalizar consulta")
    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> finishAppointment(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.finishAppointment(appointmentId));
    }

    @Operation(summary = "Cancelar  consulta")
    @PatchMapping("/{appointmentId}/cancelar")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentId));
    }

    @Operation(summary = "Deletar consulta")
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID appointmentId) {
        appointmentService.delete(appointmentId);
        return ResponseEntity.noContent().build();
    }

}

