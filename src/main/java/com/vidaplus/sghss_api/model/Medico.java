package com.vidaplus.sghss_api.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

	@NotNull(message = "É obrigatório informar a idUsuario")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario", referencedColumnName = "idUsuario")
	@JsonManagedReference("medico-usuario")
	private Usuario usuario;

	@JsonIgnore
	@OneToMany(mappedBy = "medico")
	private List<Consulta> consultas;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Medico [idMedico=" + idMedico + ", nome=" + nome + ", crm=" + crm + ", especialidade=" + especialidade
				+ ", usuario=" + usuario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(crm, especialidade, idMedico, nome, usuario);
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
				&& idMedico == other.idMedico && Objects.equals(nome, other.nome)
				&& Objects.equals(usuario, other.usuario);
	}

}
