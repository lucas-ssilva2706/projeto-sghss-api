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
import com.vidaplus.sghss_api.model.Prontuario;
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
    public List<Prontuario> findAll() {
        return prontuarioService.listarTodos();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
    public ResponseEntity<Prontuario> findById(@PathVariable Long id) {
        return prontuarioService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
    public ResponseEntity<Prontuario> findByPacienteId(@PathVariable Long pacienteId) {
        return prontuarioService.buscarPorPacienteId(pacienteId)
            .map(prontuario -> ResponseEntity.ok().body(prontuario))
            .orElse(ResponseEntity.notFound().build());
    }    

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
    public Prontuario create(@Valid @RequestBody ProntuarioDTO prontuarioDTO) {
        return prontuarioService.criarProntuario(prontuarioDTO);
    }
}