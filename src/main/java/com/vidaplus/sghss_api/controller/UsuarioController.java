package com.vidaplus.sghss_api.controller;

import java.util.List;

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

import com.vidaplus.sghss_api.dto.UsuarioDTO;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Usuario> findAll() {
        return usuarioService.listarTodos();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Usuario create(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.criarUsuario(usuarioDTO);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.atualizarUsuario(id, usuarioDTO)
            .map(updatedUsuario -> ResponseEntity.ok().body(updatedUsuario))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (usuarioService.deletarUsuario(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}