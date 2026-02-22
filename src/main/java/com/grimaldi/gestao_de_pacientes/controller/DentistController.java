package com.grimaldi.gestao_de_pacientes.controller;

import com.grimaldi.gestao_de_pacientes.dto.DentistRequest;
import com.grimaldi.gestao_de_pacientes.dto.DentistResponse;
import com.grimaldi.gestao_de_pacientes.entity.Dentist;
import com.grimaldi.gestao_de_pacientes.service.DentistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dentista")
public class DentistController {

    private final DentistService dentistService;

    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @PostMapping
    public ResponseEntity<DentistResponse> createDentist(@Valid @RequestBody DentistRequest request) {
        Dentist dentist = dentistService.createDentist(request);
        DentistResponse response = new DentistResponse(dentist);

        return ResponseEntity.status(201).body(response);
    }
}
