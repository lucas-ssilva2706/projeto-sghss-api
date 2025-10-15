package com.vidaplus.sghss_api.mapper;

import com.vidaplus.sghss_api.dto.ConsultaResponseDTO;
import com.vidaplus.sghss_api.dto.UnidadeHospitalarDTO;
import com.vidaplus.sghss_api.model.Consulta;
import com.vidaplus.sghss_api.model.UnidadeHospitalar;

public class ConsultaMapper {

	public static ConsultaResponseDTO toDTO(Consulta consulta) {
		if (consulta == null) {
			return null;
		}

		ConsultaResponseDTO dto = new ConsultaResponseDTO();
		dto.setId(consulta.getId());
		dto.setDataHoras(consulta.getDataHoras());
		dto.setSituacaoConsulta(consulta.getSituacaoConsulta());
		dto.setTipoConsulta(consulta.getTipoConsulta());

		dto.setPaciente(PacienteMapper.toDTO(consulta.getPaciente()));
		dto.setMedico(MedicoMapper.toDTO(consulta.getMedico()));
		dto.setUnidadeHospitalar(toUnidadeDTO(consulta.getUnidadeHospitalar()));

		return dto;
	}

	private static UnidadeHospitalarDTO toUnidadeDTO(UnidadeHospitalar unidade) {
		if (unidade == null) {
			return null;
		}
		UnidadeHospitalarDTO dto = new UnidadeHospitalarDTO();
		dto.setNome(unidade.getNome());
		dto.setEndereco(unidade.getEndereco());
		return dto;
	}
}