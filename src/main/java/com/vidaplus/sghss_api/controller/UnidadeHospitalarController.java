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

import com.vidaplus.sghss_api.dto.UnidadeHospitalarDTO;
import com.vidaplus.sghss_api.model.UnidadeHospitalar;
import com.vidaplus.sghss_api.service.UnidadeHospitalarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/unidadeshospitalares")
public class UnidadeHospitalarController {

    private final UnidadeHospitalarService unidadeService;

    public UnidadeHospitalarController(UnidadeHospitalarService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @GetMapping
    public List<UnidadeHospitalar> findAll() {
        return unidadeService.listarTodas();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UnidadeHospitalar> findById(@PathVariable Long id) {
        return unidadeService.buscarPorId(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UnidadeHospitalar create(@Valid @RequestBody UnidadeHospitalarDTO unidadeDTO) {
        return unidadeService.criarUnidade(unidadeDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UnidadeHospitalar> update(@PathVariable Long id, @Valid @RequestBody UnidadeHospitalarDTO unidadeDTO) {
        return unidadeService.atualizarUnidade(id, unidadeDTO)
            .map(updatedUnidade -> ResponseEntity.ok().body(updatedUnidade))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (unidadeService.deletarUnidade(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}