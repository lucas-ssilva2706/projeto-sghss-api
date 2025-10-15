package com.vidaplus.sghss_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidaplus.sghss_api.dto.RegistroProntuarioDTO;
import com.vidaplus.sghss_api.dto.RegistroProntuarioResponseDTO;
import com.vidaplus.sghss_api.service.RegistroProntuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/registrosprontuarios")
public class RegistroProntuarioController {

	private final RegistroProntuarioService registroService;

	public RegistroProntuarioController(RegistroProntuarioService registroService) {
		this.registroService = registroService;
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<RegistroProntuarioResponseDTO> findAll() {
		return registroService.listarTodos();
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
	public ResponseEntity<RegistroProntuarioResponseDTO> findById(@PathVariable long id) {
		return registroService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
	public ResponseEntity<RegistroProntuarioResponseDTO> create(@Valid @RequestBody RegistroProntuarioDTO registroDTO) {
		RegistroProntuarioResponseDTO novoRegistro = registroService.criarRegistro(registroDTO);
		return ResponseEntity.ok(novoRegistro);
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		if (registroService.deletarRegistro(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}