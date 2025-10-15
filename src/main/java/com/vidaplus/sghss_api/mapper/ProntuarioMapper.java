package com.vidaplus.sghss_api.mapper;

import java.util.stream.Collectors;

import com.vidaplus.sghss_api.dto.ProntuarioResponseDTO;
import com.vidaplus.sghss_api.model.Prontuario;

public class ProntuarioMapper {

	public static ProntuarioResponseDTO toDTO(Prontuario prontuario) {
		if (prontuario == null) {
			return null;
		}

		ProntuarioResponseDTO dto = new ProntuarioResponseDTO();
		dto.setId(prontuario.getId());
		dto.setDataCriacao(prontuario.getDataCriacao());

		dto.setPaciente(PacienteMapper.toDTO(prontuario.getPaciente()));

		if (prontuario.getRegistroProntuario() != null) {
			dto.setRegistros(prontuario.getRegistroProntuario().stream().map(RegistroProntuarioMapper::toDTO)
					.collect(Collectors.toList()));
		}

		return dto;
	}
}