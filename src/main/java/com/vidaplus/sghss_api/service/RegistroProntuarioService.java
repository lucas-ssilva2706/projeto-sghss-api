package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.RegistroProntuarioDTO;
import com.vidaplus.sghss_api.dto.RegistroProntuarioResponseDTO;
import com.vidaplus.sghss_api.mapper.RegistroProntuarioMapper;
import com.vidaplus.sghss_api.model.Consulta;
import com.vidaplus.sghss_api.model.Prontuario;
import com.vidaplus.sghss_api.model.RegistroProntuario;
import com.vidaplus.sghss_api.repository.ConsultaRepository;
import com.vidaplus.sghss_api.repository.ProntuarioRepository;
import com.vidaplus.sghss_api.repository.RegistroProntuarioRepository;

@Service
public class RegistroProntuarioService {

	private final RegistroProntuarioRepository registroRepository;
	private final ProntuarioRepository prontuarioRepository;
	private final ConsultaRepository consultaRepository;

	public RegistroProntuarioService(RegistroProntuarioRepository registroRepository,
			ProntuarioRepository prontuarioRepository, ConsultaRepository consultaRepository) {
		this.registroRepository = registroRepository;
		this.prontuarioRepository = prontuarioRepository;
		this.consultaRepository = consultaRepository;
	}

	public List<RegistroProntuarioResponseDTO> listarTodos() {
		return registroRepository.findAll().stream().map(RegistroProntuarioMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<RegistroProntuarioResponseDTO> buscarPorId(long id) {
		return registroRepository.findById(id).map(RegistroProntuarioMapper::toDTO);
	}

	public RegistroProntuarioResponseDTO criarRegistro(RegistroProntuarioDTO registroDTO) {
		Prontuario prontuario = prontuarioRepository.findById(registroDTO.getProntuarioId()).orElseThrow(
				() -> new RuntimeException("Prontuário não encontrado com o ID: " + registroDTO.getProntuarioId()));

		Consulta consulta = consultaRepository.findById(registroDTO.getConsultaId()).orElseThrow(
				() -> new RuntimeException("Consulta não encontrada com o ID: " + registroDTO.getConsultaId()));

		RegistroProntuario novoRegistro = new RegistroProntuario();
		novoRegistro.setDataHora(registroDTO.getDataHora());
		novoRegistro.setProntuario(prontuario);
		novoRegistro.setConsulta(consulta);

		RegistroProntuario registroSalvo = registroRepository.save(novoRegistro);

		return RegistroProntuarioMapper.toDTO(registroSalvo);
	}

	public boolean deletarRegistro(Long id) {
		if (registroRepository.existsById(id)) {
			registroRepository.deleteById(id);
			return true;
		}
		return false;
	}
}