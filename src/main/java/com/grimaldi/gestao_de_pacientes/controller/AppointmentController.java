package com.grimaldi.gestao_de_pacientes.controller;

import com.grimaldi.gestao_de_pacientes.dto.AppointmentResponse;
import com.grimaldi.gestao_de_pacientes.entity.Appointment;
import com.grimaldi.gestao_de_pacientes.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/consultas")
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<AppointmentResponse> CreatAppointment(@PathVariable UUID scheduleId) {
        Appointment appointment = appointmentService.creatAppointment(scheduleId);

        AppointmentResponse response = new AppointmentResponse(
                appointment.getId(), appointment.getSchedule().getDate(), appointment.getSchedule().getTime(), appointment.getStatus(), scheduleId);

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
    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> confirmAppointment(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.confirmAppointment(appointmentId));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID appointmentId) {
        appointmentService.delete(appointmentId);
        return ResponseEntity.noContent().build();
    }

}

