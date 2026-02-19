package com.grimaldi.gestao_de_pacientes.controller;

import com.grimaldi.gestao_de_pacientes.dto.PatientNoteUpdateRequest;
import com.grimaldi.gestao_de_pacientes.dto.PatientRequest;
import com.grimaldi.gestao_de_pacientes.dto.PatientResponse;
import com.grimaldi.gestao_de_pacientes.dto.PatientUpdateRequest;
import com.grimaldi.gestao_de_pacientes.entity.Patient;
import com.grimaldi.gestao_de_pacientes.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest request) {
        Patient patient =  patientService.createPatient(request);

        PatientResponse response = new PatientResponse(patient);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponse> findById(@PathVariable UUID patientId) {
        return ResponseEntity.ok(patientService.findById(patientId));
    }

    @PatchMapping("/{patientId}/anotacao")
    public ResponseEntity<PatientResponse> updateNotes(@PathVariable UUID patientId, PatientNoteUpdateRequest updateRequest) {
        return ResponseEntity.ok(patientService.updatePatientNotes(patientId, updateRequest));
    }

    @PatchMapping("/{patientId}/detalhes")
    public ResponseEntity<PatientResponse> updateDetails(@PathVariable UUID patientId, PatientUpdateRequest updateRequest) {
        return ResponseEntity.ok(patientService.updateDetails(patientId, updateRequest));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<PatientResponse> delete(@PathVariable UUID patientId) {
        patientService.delete(patientId);
        return ResponseEntity.noContent().build();
    }
}
