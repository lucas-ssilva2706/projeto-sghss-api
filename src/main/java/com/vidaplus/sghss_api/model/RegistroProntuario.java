package com.vidaplus.sghss_api.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "registros_prontuarios")
public class RegistroProntuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idRegistro;
	
	@NotBlank(message = "É obrigatório informar a data e a hora do registro")	
	@Column(name="data_hora",nullable=false)
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime dataHora;
	
	@NotBlank(message = "É obrigatório informar o idProntuario")
	@ManyToOne
	@JoinColumn(name = "id_prontuario", nullable = false)
	private Prontuario prontuario;
	
	@NotBlank(message = "É obrigatório informar o idConsulta")
	@OneToOne
	@JoinColumn(name = "id_consulta", referencedColumnName = "idConsulta")
	private Consulta consulta;

	public long getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	public void setProntuario(Prontuario prontuario) {
		this.prontuario = prontuario;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	@Override
	public String toString() {
		return "RegistroProntuario [idRegistro=" + idRegistro + ", dataHora=" + dataHora + ", prontuario=" + prontuario
				+ ", consulta=" + consulta + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(consulta, dataHora, idRegistro, prontuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroProntuario other = (RegistroProntuario) obj;
		return Objects.equals(consulta, other.consulta) && Objects.equals(dataHora, other.dataHora)
				&& idRegistro == other.idRegistro && Objects.equals(prontuario, other.prontuario);
	}
		
}
