package com.vidaplus.sghss_api.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "medicos")
public class Medico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idMedico;
	
	@NotBlank(message = "É obrigatório informar o nome do médico")
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotBlank(message = "É obrigatório informar o CRM do médico")
	@Column(name = "crm", nullable = false, unique = true)
	private String crm;
	
	@NotBlank(message = "É obrigatório informar a especialidade do médico")
	@Column(name = "especialidade", nullable = false)
	private String especialidade;

	public long getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(long idMedico) {
		this.idMedico = idMedico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	@Override
	public String toString() {
		return "Medicos [idMedico=" + idMedico + ", nome=" + nome + ", crm=" + crm + ", especialidade=" + especialidade
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(crm, especialidade, idMedico, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medico other = (Medico) obj;
		return Objects.equals(crm, other.crm) && Objects.equals(especialidade, other.especialidade)
				&& idMedico == other.idMedico && Objects.equals(nome, other.nome);
	}
	
	

}
