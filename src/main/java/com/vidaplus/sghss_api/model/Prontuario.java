package com.vidaplus.sghss_api.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "prontuarios")
public class Prontuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "É obrigatório informar a data de criação do prontuário")	
	@Column(name="data_criacao",nullable=false)
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate dataCriacao;	
	
	@NotNull(message = "É obrigatório informar a idPaciente")
	@OneToOne
	@JoinColumn(name = "id_paciente", referencedColumnName = "id")
	@JsonManagedReference("paciente-prontuario")
	private Paciente paciente;
	
	@JsonIgnore
	@OneToMany(mappedBy = "prontuario")
	private List<RegistroProntuario> registroProntuario;

	public List<RegistroProntuario> getRegistroProntuario() {
		return registroProntuario;
	}

	public void setRegistroProntuario(List<RegistroProntuario> registroProntuario) {
		this.registroProntuario = registroProntuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long idProntuario) {
		this.id = idProntuario;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public String toString() {
		return "Prontuario [id=" + id + ", dataCriacao=" + dataCriacao + ", paciente=" + paciente
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCriacao, id, paciente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prontuario other = (Prontuario) obj;
		return Objects.equals(dataCriacao, other.dataCriacao) && id == other.id
				&& Objects.equals(paciente, other.paciente);
	}
	
}
