package com.grimaldi.gestao_de_pacientes.service;

import com.grimaldi.gestao_de_pacientes.dto.DentistRequest;
import com.grimaldi.gestao_de_pacientes.entity.Dentist;
import com.grimaldi.gestao_de_pacientes.exception.DuplicateEmailException;
import com.grimaldi.gestao_de_pacientes.repository.DentistRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DentistService {

    private final DentistRepository dentistRepository;
    private final PasswordEncoder passwordEncoder;

    public DentistService(DentistRepository dentistRepository, PasswordEncoder passwordEncoder) {
        this.dentistRepository = dentistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Dentist createDentist (DentistRequest request) {
        //Validar se o e-mail já está em uso
        if (dentistRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateEmailException("E-mail já cadastrado"); // Use uma exception customizada aqui
        }

        Dentist dentist = new Dentist();

        dentist.setName(request.name());
        dentist.setEmail(request.email());

        //Criptografia obrigatória para o Spring Security
        dentist.setPassword(passwordEncoder.encode(request.password()));
        dentist.setCro(request.cro());

        //Forçar a Role correta
        dentist.setRole("ROLE_DENTIST");

        return dentistRepository.save(dentist);
    }
}
