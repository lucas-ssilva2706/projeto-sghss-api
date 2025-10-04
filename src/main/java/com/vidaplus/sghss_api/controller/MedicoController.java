package com.vidaplus.sghss_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidaplus.sghss_api.model.Medico;
import com.vidaplus.sghss_api.repository.MedicoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	private MedicoRepository repository;
	
	MedicoController(MedicoRepository medicoRepository) {
		this.repository = medicoRepository;
	}

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
	public Medico create(@Valid @RequestBody Medico medico) {
		return repository.save(medico);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody Medico medico) {
		return repository.findById(id).map(record -> {
			record.setNome(medico.getNome());
			record.setCrm(medico.getCrm());
			record.setEspecialidade(medico.getEspecialidade());
			Medico updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path = { "/{id}"})
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
