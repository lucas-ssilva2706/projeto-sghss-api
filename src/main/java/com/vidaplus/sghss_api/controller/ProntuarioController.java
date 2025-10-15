package com.vidaplus.sghss_api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vidaplus.sghss_api.dto.ProntuarioDTO;
import com.vidaplus.sghss_api.dto.ProntuarioResponseDTO;
import com.vidaplus.sghss_api.service.ProntuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

	private final ProntuarioService prontuarioService;

	public ProntuarioController(ProntuarioService prontuarioService) {
		this.prontuarioService = prontuarioService;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<ProntuarioResponseDTO> findAll() {
		return prontuarioService.listarTodos();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
	public ResponseEntity<ProntuarioResponseDTO> findById(@PathVariable long id) {
		return prontuarioService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/paciente/{pacienteId}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
	public ResponseEntity<ProntuarioResponseDTO> findByPacienteId(@PathVariable Long pacienteId) {
		return prontuarioService.buscarPorPacienteId(pacienteId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ProntuarioResponseDTO create(@Valid @RequestBody ProntuarioDTO prontuarioDTO) {
		return prontuarioService.criarProntuario(prontuarioDTO);
	}
}