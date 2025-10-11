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
    public List<RegistroProntuario> findAll() {
        return registroService.listarTodos();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RegistroProntuario> findById(@PathVariable long id) {
        return registroService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RegistroProntuario create(@Valid @RequestBody RegistroProntuario registroProntuario) {
        return registroService.criarRegistro(registroProntuario);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (registroService.deletarRegistro(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}