package com.vidaplus.sghss_api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidaplus.sghss_api.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    Optional<Consulta> findByMedico_IdAndDataHoras(Long medicoId, LocalDateTime dataHoras);
    Optional<Consulta> findByPaciente_IdAndDataHoras(Long pacienteId, LocalDateTime dataHoras);
    
    List<Consulta> findByPaciente_Id(Long pacienteId);
    List<Consulta> findByMedico_Id(Long medicoId);
    
}