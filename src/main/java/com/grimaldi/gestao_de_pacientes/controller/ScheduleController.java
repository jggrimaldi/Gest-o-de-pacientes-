package com.grimaldi.gestao_de_pacientes.controller;

import com.grimaldi.gestao_de_pacientes.dto.ScheduleRequest;
import com.grimaldi.gestao_de_pacientes.dto.ScheduleResponse;
import com.grimaldi.gestao_de_pacientes.entity.Schedule;
import com.grimaldi.gestao_de_pacientes.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    public ScheduleService service;

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody ScheduleRequest request){
        Schedule schedule = new Schedule(null,request.date(),request.time(),false);

        service.addSchedule(schedule);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public List<ScheduleResponse> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/available")
    public List<ScheduleResponse> findAllAvailable() {
        return service.findAvailable();
    }
}
