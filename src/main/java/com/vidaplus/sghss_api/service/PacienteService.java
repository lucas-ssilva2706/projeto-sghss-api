package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.model.Paciente;
import com.vidaplus.sghss_api.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente criarPaciente(Paciente paciente) {
        if (pacienteRepository.findByCpf(paciente.getCpf()).isPresent()) {
            throw new RuntimeException("Este CPF já está cadastrado.");
        }
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> atualizarPaciente(long id, Paciente pacienteOld) {
        return pacienteRepository.findById(id)
            .map(paciente -> {
                pacienteRepository.findByCpf(pacienteOld.getCpf()).ifPresent(pacienteComCpf -> {
                    if (!pacienteComCpf.getIdPaciente().equals(id)) {
                        throw new RuntimeException("O CPF " + pacienteOld.getCpf() + " já pertence a outro paciente.");
                    }
                });

                paciente.setNome(pacienteOld.getNome());
                paciente.setCpf(pacienteOld.getCpf());
                paciente.setTelefone(pacienteOld.getTelefone());
                paciente.setDataNascimento(pacienteOld.getDataNascimento());
                
                return pacienteRepository.save(paciente);
            });
    }

    public boolean deletarPaciente(long id) {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}