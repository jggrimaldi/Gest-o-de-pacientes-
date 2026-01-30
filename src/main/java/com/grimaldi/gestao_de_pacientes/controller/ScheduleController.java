package com.grimaldi.gestao_de_pacientes.controller;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleRequest;
import com.grimaldi.gestao_de_pacientes.dto.ScheduleResponse;
import com.grimaldi.gestao_de_pacientes.dto.UpdateScheduleRequest;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/horarios")
public class ScheduleController {

    @Autowired
    public ScheduleService service;

    @PostMapping
    public ResponseEntity<ScheduleResponse> add(@RequestBody ScheduleRequest request){

        Schedule save = service.addSchedule(request);

        ScheduleResponse response = new ScheduleResponse(
                save.getId(), save.getDate(), save.getTime(), save.isAvailable());

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/disponivel")
    public ResponseEntity<List<ScheduleResponse>> findAllAvailable() {
        return ResponseEntity.ok(service.findAvailable());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(schedule -> new ScheduleResponse(
                        schedule.getId(), schedule.getDate(), schedule.getTime(), schedule.isAvailable()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ScheduleResponse> update(@PathVariable UUID id, @RequestBody UpdateScheduleRequest updateRequest) {
        ScheduleResponse response = service.update(id, updateRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}/disponibilidade")
    public ResponseEntity<ScheduleResponse> availableUpdate(@PathVariable UUID id, @RequestBody UpdateScheduleRequest updateAvailableRequest) {
        ScheduleResponse response = service.updateAvailable(id, updateAvailableRequest);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
