package com.vidaplus.sghss_api.dto;

import java.time.LocalDateTime;

public class RegistroProntuarioResponseDTO {

	private Long id;
	private LocalDateTime dataHora;
	private Long consultaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Long getConsultaId() {
		return consultaId;
	}

	public void setConsultaId(Long consultaId) {
		this.consultaId = consultaId;
	}
}