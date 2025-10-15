package com.vidaplus.sghss_api.dto;

import java.time.LocalDate;
import java.util.List;

public class ProntuarioResponseDTO {

	private Long id;
	private LocalDate dataCriacao;
	private PacienteResponseDTO paciente;
	private List<RegistroProntuarioResponseDTO> registros;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate localDate) {
		this.dataCriacao = localDate;
	}

	public PacienteResponseDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteResponseDTO paciente) {
		this.paciente = paciente;
	}

	public List<RegistroProntuarioResponseDTO> getRegistros() {
		return registros;
	}

	public void setRegistros(List<RegistroProntuarioResponseDTO> registros) {
		this.registros = registros;
	}
}