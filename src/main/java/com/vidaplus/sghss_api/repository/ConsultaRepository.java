package com.vidaplus.sghss_api.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vidaplus.sghss_api.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Optional<Consulta> findByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);
    Optional<Consulta> findByPacienteIdAndDataHora(Long pacienteId, LocalDateTime dataHora);
    
}