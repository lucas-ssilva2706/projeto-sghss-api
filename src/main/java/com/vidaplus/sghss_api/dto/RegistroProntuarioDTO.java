package com.vidaplus.sghss_api.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public class RegistroProntuarioDTO {

	@NotNull(message = "A data e a hora são obrigatórias.")
	private LocalDateTime dataHora;

	@NotNull(message = "O ID do prontuário é obrigatório.")
	private Long prontuarioId;

	@NotNull(message = "O ID da consulta é obrigatório.")
	private Long consultaId;

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Long getProntuarioId() {
		return prontuarioId;
	}

	public void setProntuarioId(Long prontuarioId) {
		this.prontuarioId = prontuarioId;
	}

	public Long getConsultaId() {
		return consultaId;
	}

	public void setConsultaId(Long consultaId) {
		this.consultaId = consultaId;
	}
}