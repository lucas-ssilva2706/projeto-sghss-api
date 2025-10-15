package com.vidaplus.sghss_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.ProntuarioDTO;
import com.vidaplus.sghss_api.dto.ProntuarioResponseDTO;
import com.vidaplus.sghss_api.mapper.ProntuarioMapper;
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

	public List<ProntuarioResponseDTO> listarTodos() {
		return prontuarioRepository.findAll().stream().map(ProntuarioMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<ProntuarioResponseDTO> buscarPorId(long id) {
		return prontuarioRepository.findById(id).map(ProntuarioMapper::toDTO);
	}

	public Optional<ProntuarioResponseDTO> buscarPorPacienteId(Long pacienteId) {
		return prontuarioRepository.findByPaciente_Id(pacienteId).map(ProntuarioMapper::toDTO);
	}

	public ProntuarioResponseDTO criarProntuario(ProntuarioDTO prontuarioDTO) {
		Long pacienteId = prontuarioDTO.getPacienteId();

		Paciente paciente = pacienteRepository.findById(pacienteId)
				.orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + pacienteId));

		if (prontuarioRepository.findByPaciente_Id(pacienteId).isPresent()) {
			throw new RuntimeException("Este paciente já possui um prontuário cadastrado.");
		}

		Prontuario novoProntuario = new Prontuario();
		novoProntuario.setPaciente(paciente);
		novoProntuario.setDataCriacao(LocalDate.now());

		Prontuario prontuarioSalvo = prontuarioRepository.save(novoProntuario);
		return ProntuarioMapper.toDTO(prontuarioSalvo);
	}
}