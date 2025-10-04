package com.vidaplus.sghss_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidaplus.sghss_api.model.RegistroProntuario;
import com.vidaplus.sghss_api.repository.RegistroProntuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/registrosprontuarios")
public class RegistroProntuarioController {
	private RegistroProntuarioRepository repository;
	
	@GetMapping
	public List<?> findAll() {
		return repository.findAll();
	}
	
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
					.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public RegistroProntuario create(@Valid @RequestBody RegistroProntuario registroProntuario) {
		return repository.save(registroProntuario);
	}
	
	@DeleteMapping(path = { "/{id}"})
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
