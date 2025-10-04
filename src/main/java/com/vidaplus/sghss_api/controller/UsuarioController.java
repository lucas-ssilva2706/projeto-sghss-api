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

import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	private UsuarioRepository repository;
	
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
	public Usuario create(@Valid @RequestBody Usuario usuario) {
		return repository.save(usuario);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody Usuario usuario) {
		return repository.findById(id).map(record -> {
			record.setEmail(usuario.getEmail());
			record.setSenha(usuario.getSenha());
			record.setTipoUsuario(usuario.getTipoUsuario());
			Usuario updated = repository.save(record);
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
