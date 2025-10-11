package com.vidaplus.sghss_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.model.Consulta;
import com.vidaplus.sghss_api.model.enums.SituacaoConsulta;
import com.vidaplus.sghss_api.repository.ConsultaRepository;
import com.vidaplus.sghss_api.repository.MedicoRepository;
import com.vidaplus.sghss_api.repository.PacienteRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarPorId(long id) {
        return consultaRepository.findById(id);
    }

    public Consulta criarConsulta(Consulta consulta) {
        pacienteRepository.findById(consulta.getPaciente().getIdPaciente())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));
        medicoRepository.findById(consulta.getMedico().getIdMedico())
            .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

        LocalDateTime dataHora = consulta.getDataHoras();
        Long medicoId = consulta.getMedico().getIdMedico();
        Long pacienteId = consulta.getPaciente().getIdPaciente();

        if (consultaRepository.findByMedicoIdAndDataHora(medicoId, dataHora).isPresent()) {
            throw new RuntimeException("O médico já possui uma consulta agendada para este horário.");
        }

        if (consultaRepository.findByPacienteIdAndDataHora(pacienteId, dataHora).isPresent()) {
            throw new RuntimeException("O paciente já possui uma consulta agendada para este horário.");
        }

        consulta.setSituacaoConsulta(SituacaoConsulta.AGENDADA);

        return consultaRepository.save(consulta);
    }

    public boolean deletarConsulta(long id) {
        if (consultaRepository.existsById(id)) {
            consultaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}