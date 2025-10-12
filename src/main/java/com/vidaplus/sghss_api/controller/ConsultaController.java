package com.vidaplus.sghss_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidaplus.sghss_api.dto.ConsultaDTO;
import com.vidaplus.sghss_api.model.Consulta;
import com.vidaplus.sghss_api.service.ConsultaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Consulta> findAll() {
        return consultaService.listarTodas();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
    public ResponseEntity<Consulta> findById(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO')")
    public Consulta create(@Valid @RequestBody ConsultaDTO consultaDTO) {
        return consultaService.criarConsulta(consultaDTO);
    }

    @PatchMapping("/{id}/realizar")
    @PreAuthorize("hasAnyAuthority('MEDICO', 'ADMIN')")
    public ResponseEntity<Consulta> realizarConsulta(@PathVariable Long id) {
        return consultaService.realizarConsulta(id)
            .map(consulta -> ResponseEntity.ok().body(consulta))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MEDICO') or @consultaService.buscarPorId(#id).get().getPaciente().getUsuario().getEmail() == authentication.principal.username")
    public ResponseEntity<Consulta> cancelarConsulta(@PathVariable Long id) {
        return consultaService.cancelarConsulta(id)
            .map(consulta -> ResponseEntity.ok().body(consulta))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{id}/faltar")
    @PreAuthorize("hasAnyAuthority('MEDICO', 'ADMIN')")
    public ResponseEntity<Consulta> informarFaltaConsulta(@PathVariable Long id) {
        return consultaService.informarFaltaConsulta(id)
            .map(consulta -> ResponseEntity.ok().body(consulta))
            .orElse(ResponseEntity.notFound().build());
    }
}