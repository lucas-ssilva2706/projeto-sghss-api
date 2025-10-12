package com.vidaplus.sghss_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.ProntuarioDTO;
import com.vidaplus.sghss_api.model.Paciente;
import com.vidaplus.sghss_api.model.Prontuario;
import com.vidaplus.sghss_api.repository.PacienteRepository;
import com.vidaplus.sghss_api.repository.ProntuarioRepository;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final PacienteRepository pacienteRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository, PacienteRepository pacienteRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public List<Prontuario> listarTodos() {
        return prontuarioRepository.findAll();
    }

    public Optional<Prontuario> buscarPorId(Long id) {
        return prontuarioRepository.findById(id);
    }
    
    public Optional<Prontuario> buscarPorPacienteId(Long pacienteId) {
        return prontuarioRepository.findByPaciente_Id(pacienteId);
    }    
   
    public Prontuario criarProntuario(ProntuarioDTO prontuarioDTO) {
        Long pacienteId = prontuarioDTO.getPacienteId();
        Paciente paciente = pacienteRepository.findById(pacienteId)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + pacienteId));
        
        if (prontuarioRepository.findByPaciente_Id(pacienteId).isPresent()) {
            throw new RuntimeException("Este paciente já possui um prontuário cadastrado.");
        }

        Prontuario novoProntuario = new Prontuario();
        novoProntuario.setPaciente(paciente);
        novoProntuario.setDataCriacao(LocalDate.now());

        return prontuarioRepository.save(novoProntuario);
    }
}