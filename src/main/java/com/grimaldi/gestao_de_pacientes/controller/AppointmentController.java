package com.grimaldi.gestao_de_pacientes.controller;

import com.grimaldi.gestao_de_pacientes.dto.AppointmentNoteUpdateRequest;
import com.grimaldi.gestao_de_pacientes.dto.AppointmentRequest;
import com.grimaldi.gestao_de_pacientes.dto.AppointmentResponse;
import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/consultas")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> CreatAppointment(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentService.creatAppointment(request);

        AppointmentResponse response = new AppointmentResponse(appointment);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<AppointmentResponse>> getAgenda(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(appointmentService.getAgenda(startDate,endDate));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.findById(appointmentId));
    }

    @PatchMapping("/{appointmentId}/anotacao")
    public  ResponseEntity<AppointmentResponse> UpdateNotePad(@PathVariable UUID appointmentId , @RequestBody AppointmentNoteUpdateRequest updateRequest) {
        return ResponseEntity.ok(appointmentService.updateNotePad(appointmentId, updateRequest));
    }

    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> confirmAppointment(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.finishAppointment(appointmentId));
    }

    @PatchMapping("/{appointmentId}/cancelar")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentId));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID appointmentId) {
        appointmentService.delete(appointmentId);
        return ResponseEntity.noContent().build();
    }

}

