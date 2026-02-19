package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.PatientRequest;
import com.grimaldi.gestao_de_pacientes.dto.PatientResponse;
import com.grimaldi.gestao_de_pacientes.entity.Patient;
import com.grimaldi.gestao_de_pacientes.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient creatPatient(PatientRequest request) {
        Patient patient = new Patient();

        patient.setName(request.name());
        patient.setCpf(request.cpf());
        patient.setPhone(request.phone());
        patient.setAge(request.age());

        return patientRepository.save(patient);
    }

    public List<PatientResponse> findAll(){
        List<Patient> patients = patientRepository.findAll();

        //Transforma a lista em uma esteira, transforma as entidades em objetos DTO e transforma em lista
        return patients.stream()
                .map(PatientResponse::new)
                .toList();
    }

}
