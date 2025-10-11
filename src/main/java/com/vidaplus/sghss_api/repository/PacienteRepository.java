package com.vidaplus.sghss_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidaplus.sghss_api.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

	Optional<Paciente> findByCpf(String cpf);
    Optional<Paciente> findByUsuario_Id(Long usuarioId);	
	
}
