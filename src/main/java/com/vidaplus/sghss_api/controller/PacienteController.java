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

import com.vidaplus.sghss_api.model.Paciente;
import com.vidaplus.sghss_api.repository.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	private PacienteRepository repository;
	
	PacienteController(PacienteRepository pacienteRepositorio) {
		this.repository = pacienteRepositorio;
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
	public Paciente create(@RequestBody Paciente paciente) {
		return repository.save(paciente);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Paciente paciente) {
		return repository.findById(id).map(record -> {
			record.setNome(paciente.getNome());
			record.setCpf(paciente.getCpf());
			record.setTelefone(paciente.getTelefone());
			record.setDataNascimento(paciente.getDataNascimento());		
			Paciente updated = repository.save(record);
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
