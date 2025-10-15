package com.vidaplus.sghss_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidaplus.sghss_api.dto.ConsultaResponseDTO;
import com.vidaplus.sghss_api.dto.PacienteDTO;
import com.vidaplus.sghss_api.dto.PacienteResponseDTO;
import com.vidaplus.sghss_api.service.ConsultaService;
import com.vidaplus.sghss_api.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	private final PacienteService pacienteService;
	private final ConsultaService consultaService;

	public PacienteController(PacienteService pacienteService, ConsultaService consultaService) {
		this.pacienteService = pacienteService;
		this.consultaService = consultaService;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<PacienteResponseDTO> findAll() {
		return pacienteService.listarTodos();
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
	public ResponseEntity<PacienteResponseDTO> findById(@PathVariable long id) {
		return pacienteService.buscarPorId(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/meu-perfil")
	@PreAuthorize("hasAuthority('PACIENTE')")
	public ResponseEntity<PacienteResponseDTO> getMeuPerfil() {
		PacienteResponseDTO pacienteDTO = pacienteService.buscarMeuPerfil();
		return ResponseEntity.ok(pacienteDTO);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PacienteResponseDTO> create(@Valid @RequestBody PacienteDTO pacienteDTO) {
		PacienteResponseDTO novoPaciente = pacienteService.criarPaciente(pacienteDTO);
		return new ResponseEntity<>(novoPaciente, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PacienteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PacienteDTO pacienteDTO) {
		return pacienteService.atualizarPaciente(id, pacienteDTO)
				.map(updatedPaciente -> ResponseEntity.ok().body(updatedPaciente))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (pacienteService.deletarPaciente(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{idPaciente}/historico")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
	public ResponseEntity<List<ConsultaResponseDTO>> getHistoricoPaciente(@PathVariable Long idPaciente) {
		List<ConsultaResponseDTO> historico = consultaService.buscarHistoricoPorPacienteId(idPaciente);
		return ResponseEntity.ok(historico);
	}

	@GetMapping("/meu-historico")
	@PreAuthorize("hasAuthority('PACIENTE')")
	public ResponseEntity<List<ConsultaResponseDTO>> getMeuHistorico() {
		PacienteResponseDTO paciente = pacienteService.buscarMeuPerfil();
		List<ConsultaResponseDTO> historico = consultaService.buscarHistoricoPorPacienteId(paciente.getId());
		return ResponseEntity.ok(historico);
	}
}