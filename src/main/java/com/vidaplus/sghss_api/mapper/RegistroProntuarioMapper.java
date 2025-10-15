package com.vidaplus.sghss_api.mapper;

import com.vidaplus.sghss_api.dto.RegistroProntuarioResponseDTO;
import com.vidaplus.sghss_api.model.RegistroProntuario;

public class RegistroProntuarioMapper {

	public static RegistroProntuarioResponseDTO toDTO(RegistroProntuario registro) {
		if (registro == null) {
			return null;
		}

		RegistroProntuarioResponseDTO dto = new RegistroProntuarioResponseDTO();
		dto.setId(registro.getId());
		dto.setDataHora(registro.getDataHora());

		if (registro.getConsulta() != null) {
			dto.setConsultaId(registro.getConsulta().getId());
		}

		return dto;
	}
}