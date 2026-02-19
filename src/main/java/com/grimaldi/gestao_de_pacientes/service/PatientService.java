package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.PatientNoteUpdateRequest;
import com.grimaldi.gestao_de_pacientes.dto.PatientRequest;
import com.grimaldi.gestao_de_pacientes.dto.PatientResponse;
import com.grimaldi.gestao_de_pacientes.dto.PatientUpdateRequest;
import com.grimaldi.gestao_de_pacientes.entity.Patient;
import com.grimaldi.gestao_de_pacientes.exception.DuplicatePhoneException;
import com.grimaldi.gestao_de_pacientes.exception.IdNotExistException;
import com.grimaldi.gestao_de_pacientes.repository.PatientRepository;
import com.grimaldi.gestao_de_pacientes.service.validation.CreatePatientValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final List<CreatePatientValidation> createPatientValidations;

    public PatientService(PatientRepository patientRepository, List<CreatePatientValidation> createPatientValidations) {
        this.patientRepository = patientRepository;
        this.createPatientValidations = createPatientValidations;
    }

    @Transactional
    public Patient createPatient(PatientRequest request) {
        createPatientValidations.forEach(v-> v.validate(request));
        Patient patient = new Patient();

        patient.setName(request.name());
        patient.setCpf(request.cpf());
        patient.setPhone(request.phone());
        patient.setAge(request.age());

        return patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> findAll(){
        List<Patient> patients = patientRepository.findAll();

        //Transforma a lista em uma esteira, transforma as entidades em objetos DTO e transforma em lista
        return patients.stream()
                .map(PatientResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PatientResponse findById(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IdNotExistException("Id não existe"));

        return new PatientResponse(patient);
    }

    @Transactional
    public PatientResponse updatePatientNotes(UUID patientId, PatientNoteUpdateRequest updateRequest){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IdNotExistException("Id não existe"));

        if (updateRequest.notes() != null) {
            patient.setNotes(updateRequest.notes());
        }
        if (updateRequest.imageUrl() != null) {
            patient.setImageUrl(updateRequest.imageUrl());
        }

        return new PatientResponse(patientRepository.save(patient));
    }

    @Transactional
    public PatientResponse updateDetails(UUID patientId, PatientUpdateRequest updateRequest) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IdNotExistException("Id não existe"));

        //So valida se n for nulo e n for igual ao antigo telefone
        if (updateRequest.phone() != null && !updateRequest.phone().equals(patient.getPhone())) {
            if (patientRepository.findByPhone(updateRequest.phone()).isPresent()) {
                throw new DuplicatePhoneException("Este telefone já está em uso por outro paciente");
            }
            patient.setPhone(updateRequest.phone());
        }
        if (updateRequest.name() != null) {
            patient.setName(updateRequest.name());
        }
        if (updateRequest.age() != null) {
            patient.setAge(updateRequest.age());
        }

        return new PatientResponse(patientRepository.save(patient));
    }

    @Transactional
    public void delete(UUID patientId) {
        patientRepository.deleteById(patientId);
    }
}
