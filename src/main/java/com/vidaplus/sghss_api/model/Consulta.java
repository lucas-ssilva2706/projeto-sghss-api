package com.vidaplus.sghss_api.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.vidaplus.sghss_api.model.enums.SituacaoConsulta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "consultas")
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idConsulta;
	
	@NotBlank(message = "É obrigatório informar a data e a hora da consulta")	
	@Column(name="data_hora",nullable=false)
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime dataHoras;
	
	@NotBlank(message = "É obrigatório informar a situação da consulta")
	@Column(name="situacao", nullable = false)
	@Enumerated(EnumType.STRING)
	private SituacaoConsulta situacaoConsulta;
	
	@NotBlank(message = "É obrigatório informar o id do Paciente")
	@ManyToOne
	@JoinColumn(name = "id_paciente", nullable = false)
	private Paciente paciente;
	
	@NotBlank(message = "É obrigatório informar o id do Médico")
	@ManyToOne
	@JoinColumn(name = "id_medico", nullable = false)
	private Medico medico;
	
	@NotBlank(message = "É obrigatório informar o id da Unidade Hospitalar")
	@ManyToOne
	@JoinColumn(name = "id_unidade_hospitalar")
	private UnidadeHospitalar unidadeHospitalar;

	public long getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(long idConsulta) {
		this.idConsulta = idConsulta;
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public UnidadeHospitalar getUnidadeHospitalar() {
		return unidadeHospitalar;
	}

	public void setUnidadeHospitalar(UnidadeHospitalar unidadeHospitalar) {
		this.unidadeHospitalar = unidadeHospitalar;
	}

	@Override
	public String toString() {
		return "Consulta [idConsulta=" + idConsulta + ", dataHoras=" + dataHoras + ", situacaoConsulta="
				+ situacaoConsulta + ", paciente=" + paciente + ", medico=" + medico + ", unidadeHospitalar="
				+ unidadeHospitalar + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataHoras, idConsulta, medico, paciente, situacaoConsulta, unidadeHospitalar);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consulta other = (Consulta) obj;
		return Objects.equals(dataHoras, other.dataHoras) && idConsulta == other.idConsulta
				&& Objects.equals(medico, other.medico) && Objects.equals(paciente, other.paciente)
				&& situacaoConsulta == other.situacaoConsulta
				&& Objects.equals(unidadeHospitalar, other.unidadeHospitalar);
	}
	
	

}
