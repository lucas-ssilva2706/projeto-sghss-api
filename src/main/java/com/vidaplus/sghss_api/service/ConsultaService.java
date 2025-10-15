package com.vidaplus.sghss_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.ConsultaDTO;
import com.vidaplus.sghss_api.dto.ConsultaResponseDTO;
import com.vidaplus.sghss_api.mapper.ConsultaMapper;
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

	public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository,
			MedicoRepository medicoRepository, UnidadeHospitalarRepository unidadeRepository) {
		this.consultaRepository = consultaRepository;
		this.pacienteRepository = pacienteRepository;
		this.medicoRepository = medicoRepository;
		this.unidadeRepository = unidadeRepository;
	}

	public List<ConsultaResponseDTO> listarTodas() {
		return consultaRepository.findAll().stream().map(ConsultaMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<ConsultaResponseDTO> buscarPorId(long id) {
		return consultaRepository.findById(id).map(ConsultaMapper::toDTO);
	}

	public ConsultaResponseDTO criarConsulta(ConsultaDTO consultaDTO) {

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

		Consulta consultaSalva = consultaRepository.save(novaConsulta);

		return ConsultaMapper.toDTO(consultaSalva);
	}

	public Optional<ConsultaResponseDTO> realizarConsulta(Long id) {
		return consultaRepository.findById(id).map(consulta -> {
			if (consulta.getSituacaoConsulta() != SituacaoConsulta.AGENDADA) {
				throw new IllegalStateException(
						"Apenas consultas com a situação 'AGENDADA' podem ser marcadas como 'REALIZADA'.");
			}
			consulta.setSituacaoConsulta(SituacaoConsulta.REALIZADA);
			Consulta consultaSalva = consultaRepository.save(consulta);
			return ConsultaMapper.toDTO(consultaSalva);
		});
	}

	public Optional<ConsultaResponseDTO> cancelarConsulta(Long id) {
		return consultaRepository.findById(id).map(consulta -> {
			if (consulta.getSituacaoConsulta() == SituacaoConsulta.REALIZADA) {
				throw new IllegalStateException("Não é possível cancelar uma consulta que já foi realizada.");
			}
			consulta.setSituacaoConsulta(SituacaoConsulta.CANCELADA);
			Consulta consultaSalva = consultaRepository.save(consulta);
			return ConsultaMapper.toDTO(consultaSalva);
		});
	}

	public Optional<ConsultaResponseDTO> informarFaltaConsulta(Long id) {
		return consultaRepository.findById(id).map(consulta -> {
			if (consulta.getSituacaoConsulta() == SituacaoConsulta.REALIZADA) {
				throw new IllegalStateException("Não é possível informar falta numa consulta que já foi realizada.");
			}
			consulta.setSituacaoConsulta(SituacaoConsulta.NAO_COMPARECEU);
			Consulta consultaSalva = consultaRepository.save(consulta);
			return ConsultaMapper.toDTO(consultaSalva);
		});
	}

	public List<ConsultaResponseDTO> buscarHistoricoPorPacienteId(Long pacienteId) {
		return consultaRepository.findByPaciente_Id(pacienteId).stream().map(ConsultaMapper::toDTO)
				.collect(Collectors.toList());
	}

	public List<ConsultaResponseDTO> buscarConsultasPorMedicoId(Long medicoId) {
		return consultaRepository.findByMedico_Id(medicoId).stream().map(ConsultaMapper::toDTO)
				.collect(Collectors.toList());
	}
}