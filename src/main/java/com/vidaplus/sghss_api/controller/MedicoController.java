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

import com.vidaplus.sghss_api.dto.ConsultaResponseDTO;
import com.vidaplus.sghss_api.dto.MedicoDTO;
import com.vidaplus.sghss_api.dto.MedicoResponseDTO;
import com.vidaplus.sghss_api.service.ConsultaService;
import com.vidaplus.sghss_api.service.MedicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;
    private final ConsultaService consultaService;

    public MedicoController(MedicoService medicoService, ConsultaService consultaService) {
        this.medicoService = medicoService;
        this.consultaService = consultaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<MedicoResponseDTO> findAll() {
        return medicoService.listarTodos();
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MedicoResponseDTO> findById(@PathVariable long id) {
        return medicoService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/meu-perfil")
    @PreAuthorize("hasAuthority('MEDICO')")
    public ResponseEntity<MedicoResponseDTO> getMeuPerfil() {
        MedicoResponseDTO medicoDTO = medicoService.buscarMeuPerfil();
        return ResponseEntity.ok(medicoDTO);
    }   

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public MedicoResponseDTO create(@Valid @RequestBody MedicoDTO medicoDTO) {
        return medicoService.criarMedico(medicoDTO);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MedicoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MedicoDTO medicoDTO) {
        return medicoService.atualizarMedico(id, medicoDTO)
            .map(updatedMedico -> ResponseEntity.ok().body(updatedMedico))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (medicoService.deletarMedico(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/minhas-consultas")
    @PreAuthorize("hasAuthority('MEDICO')")
    public ResponseEntity<List<ConsultaResponseDTO>> getMinhasConsultas() {
        MedicoResponseDTO medico = medicoService.buscarMeuPerfil();
        List<ConsultaResponseDTO> consultas = consultaService.buscarConsultasPorMedicoId(medico.getId());
        return ResponseEntity.ok(consultas);
    }   
}