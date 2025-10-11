package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vidaplus.sghss_api.model.Prontuario;
import com.vidaplus.sghss_api.repository.ProntuarioRepository;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
    }

    public List<Prontuario> listarTodos() {
        return prontuarioRepository.findAll();
    }

    public Optional<Prontuario> buscarPorId(long id) {
        return prontuarioRepository.findById(id);
    }

    public Prontuario criarProntuario(Prontuario prontuario) {
        if (prontuario.getPaciente() == null || prontuario.getPaciente().getIdPaciente() == null) {
            throw new IllegalArgumentException("O paciente associado ao prontuário não pode ser nulo.");
        }

        Long pacienteId = prontuario.getPaciente().getIdPaciente();
        if (prontuarioRepository.findByPacienteId(pacienteId).isPresent()) {
            throw new RuntimeException("Este paciente já possui um prontuário cadastrado.");
        }

        return prontuarioRepository.save(prontuario);
    }
}