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
import com.vidaplus.sghss_api.service.PacienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Paciente> findAll() {
        return pacienteService.listarTodos();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable long id) {
        return pacienteService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Paciente create(@Valid @RequestBody Paciente paciente) {
        return pacienteService.criarPaciente(paciente);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Paciente> update(@PathVariable long id, @Valid @RequestBody Paciente paciente) {
        return pacienteService.atualizarPaciente(id, paciente)
            .map(updatedPaciente -> ResponseEntity.ok().body(updatedPaciente))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (pacienteService.deletarPaciente(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}