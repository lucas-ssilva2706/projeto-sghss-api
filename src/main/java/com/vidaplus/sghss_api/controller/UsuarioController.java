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
import com.vidaplus.sghss_api.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping
    public List<Usuario> findAll() {
        return usuarioService.listarTodos();
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable long id) {
        return usuarioService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Usuario create(@Valid @RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Usuario> update(@PathVariable long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(id, usuario)
            .map(updatedUser -> ResponseEntity.ok().body(updatedUser))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (usuarioService.deletarUsuario(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}