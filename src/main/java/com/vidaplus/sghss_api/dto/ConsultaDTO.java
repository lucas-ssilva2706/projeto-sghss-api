package com.vidaplus.sghss_api.dto;

import java.time.LocalDateTime;
import com.vidaplus.sghss_api.model.enums.TipoConsulta;
import jakarta.validation.constraints.NotNull;

public class ConsultaDTO {

	@NotNull(message = "A data e a hora da consulta são obrigatórias.")
	private LocalDateTime dataHoras;

	@NotNull(message = "O tipo da consulta é obrigatório.")
	private TipoConsulta tipoConsulta;

	@NotNull(message = "O ID do paciente é obrigatório.")
	private Long pacienteId;

	@NotNull(message = "O ID do médico é obrigatório.")
	private Long medicoId;

	private Long unidadeHospitalarId;

	public LocalDateTime getDataHoras() {
		return dataHoras;
	}

	public void setDataHoras(LocalDateTime dataHoras) {
		this.dataHoras = dataHoras;
	}

	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(TipoConsulta tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}

	public Long getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}

	public Long getUnidadeHospitalarId() {
		return unidadeHospitalarId;
	}

	public void setUnidadeHospitalarId(Long unidadeHospitalarId) {
		this.unidadeHospitalarId = unidadeHospitalarId;
	}
}