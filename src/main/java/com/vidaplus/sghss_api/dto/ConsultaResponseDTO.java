package com.vidaplus.sghss_api.dto;

import java.time.LocalDateTime;

import com.vidaplus.sghss_api.model.enums.SituacaoConsulta;
import com.vidaplus.sghss_api.model.enums.TipoConsulta;

public class ConsultaResponseDTO {

	private Long id;
	private LocalDateTime dataHoras;
	private SituacaoConsulta situacaoConsulta;
	private TipoConsulta tipoConsulta;

	private PacienteResponseDTO paciente;
	private MedicoResponseDTO medico;
	private UnidadeHospitalarDTO unidadeHospitalar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHoras() {
		return dataHoras;
	}

	public void setDataHoras(LocalDateTime dataHoras) {
		this.dataHoras = dataHoras;
	}

	public SituacaoConsulta getSituacaoConsulta() {
		return situacaoConsulta;
	}

	public void setSituacaoConsulta(SituacaoConsulta situacaoConsulta) {
		this.situacaoConsulta = situacaoConsulta;
	}

	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(TipoConsulta tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public PacienteResponseDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteResponseDTO paciente) {
		this.paciente = paciente;
	}

	public MedicoResponseDTO getMedico() {
		return medico;
	}

	public void setMedico(MedicoResponseDTO medico) {
		this.medico = medico;
	}

	public UnidadeHospitalarDTO getUnidadeHospitalar() {
		return unidadeHospitalar;
	}

	public void setUnidadeHospitalar(UnidadeHospitalarDTO unidadeHospitalar) {
		this.unidadeHospitalar = unidadeHospitalar;
	}
}