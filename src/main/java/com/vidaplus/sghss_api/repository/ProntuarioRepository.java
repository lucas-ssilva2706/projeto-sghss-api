package com.vidaplus.sghss_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vidaplus.sghss_api.model.Prontuario;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    Optional<Prontuario> findByPaciente_Id(Long pacienteId);
    
}