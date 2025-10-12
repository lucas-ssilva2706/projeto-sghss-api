package com.vidaplus.sghss_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.ConsultaDTO;
import com.vidaplus.sghss_api.model.Consulta;
import com.vidaplus.sghss_api.model.Medico;
import com.vidaplus.sghss_api.model.Paciente;
import com.vidaplus.sghss_api.model.UnidadeHospitalar;
import com.vidaplus.sghss_api.model.enums.SituacaoConsulta;
import com.vidaplus.sghss_api.model.enums.TipoConsulta;
import com.vidaplus.sghss_api.repository.ConsultaRepository;
import com.vidaplus.sghss_api.repository.MedicoRepository;
import com.vidaplus.sghss_api.repository.PacienteRepository;
import com.vidaplus.sghss_api.repository.UnidadeHospitalarRepository;

@Service
public class ConsultaService {

	private final ConsultaRepository consultaRepository;
	private final PacienteRepository pacienteRepository;
	private final MedicoRepository medicoRepository;
	private final UnidadeHospitalarRepository unidadeRepository;

	// Injetamos todos os repositórios necessários para as validações
	public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository,
			MedicoRepository medicoRepository, UnidadeHospitalarRepository unidadeRepository) {
		this.consultaRepository = consultaRepository;
		this.pacienteRepository = pacienteRepository;
		this.medicoRepository = medicoRepository;
		this.unidadeRepository = unidadeRepository;
	}

	public List<Consulta> listarTodas() {
		return consultaRepository.findAll();
	}

	public Optional<Consulta> buscarPorId(long id) {
		return consultaRepository.findById(id);
	}

	public Consulta criarConsulta(ConsultaDTO consultaDTO) {

		if (consultaDTO.getTipoConsulta() == TipoConsulta.PRESENCIAL && consultaDTO.getUnidadeHospitalarId() == null) {
			throw new IllegalArgumentException(
					"Para consultas do tipo PRESENCIAL, a Unidade Hospitalar é obrigatória.");
		}

		Paciente paciente = pacienteRepository.findById(consultaDTO.getPacienteId()).orElseThrow(
				() -> new RuntimeException("Paciente não encontrado com o ID: " + consultaDTO.getPacienteId()));

		Medico medico = medicoRepository.findById(consultaDTO.getMedicoId()).orElseThrow(
				() -> new RuntimeException("Médico não encontrado com o ID: " + consultaDTO.getMedicoId()));

		UnidadeHospitalar unidade = null;
		if (consultaDTO.getUnidadeHospitalarId() != null) {
			unidade = unidadeRepository.findById(consultaDTO.getUnidadeHospitalarId())
					.orElseThrow(() -> new RuntimeException(
							"Unidade Hospitalar não encontrada com o ID: " + consultaDTO.getUnidadeHospitalarId()));
		}

		LocalDateTime dataHora = consultaDTO.getDataHoras();

		if (consultaRepository.findByMedico_IdAndDataHoras(medico.getId(), dataHora).isPresent()) {
			throw new RuntimeException("O médico já possui uma consulta agendada para este horário.");
		}

		if (consultaRepository.findByPaciente_IdAndDataHoras(paciente.getId(), dataHora).isPresent()) {
			throw new RuntimeException("O paciente já possui uma consulta agendada para este horário.");
		}

		Consulta novaConsulta = new Consulta();
		novaConsulta.setDataHoras(dataHora);
		novaConsulta.setTipoConsulta(consultaDTO.getTipoConsulta());
		novaConsulta.setSituacaoConsulta(SituacaoConsulta.AGENDADA);
		novaConsulta.setPaciente(paciente);
		novaConsulta.setMedico(medico);
		novaConsulta.setUnidadeHospitalar(unidade);

		return consultaRepository.save(novaConsulta);
	}

	public Optional<Consulta> realizarConsulta(Long id) {
		return consultaRepository.findById(id).map(consulta -> {
			if (consulta.getSituacaoConsulta() != SituacaoConsulta.AGENDADA) {
				throw new IllegalStateException(
						"Apenas consultas com a situação 'AGENDADA' podem ser marcadas como 'REALIZADA'.");
			}
			consulta.setSituacaoConsulta(SituacaoConsulta.REALIZADA);
			return consultaRepository.save(consulta);
		});
	}

	public Optional<Consulta> cancelarConsulta(Long id) {
		return consultaRepository.findById(id).map(consulta -> {
			if (consulta.getSituacaoConsulta() == SituacaoConsulta.REALIZADA) {
				throw new IllegalStateException("Não é possível cancelar uma consulta que já foi realizada.");
			}
			consulta.setSituacaoConsulta(SituacaoConsulta.CANCELADA);
			return consultaRepository.save(consulta);
		});
	}

	public Optional<Consulta> informarFaltaConsulta(Long id) {
		return consultaRepository.findById(id).map(consulta -> {
			if (consulta.getSituacaoConsulta() == SituacaoConsulta.REALIZADA) {
				throw new IllegalStateException("Não é possível informar falta numa consulta que já foi realizada.");
			}
			consulta.setSituacaoConsulta(SituacaoConsulta.NAO_COMPARECEU);
			return consultaRepository.save(consulta);
		});
	}
	
    public List<Consulta> buscarHistoricoPorPacienteId(Long pacienteId) {
        return consultaRepository.findByPaciente_Id(pacienteId);
    }
    
    public List<Consulta> buscarConsultasPorMedicoId(Long medicoId) {
        return consultaRepository.findByMedico_Id(medicoId);
    }
}