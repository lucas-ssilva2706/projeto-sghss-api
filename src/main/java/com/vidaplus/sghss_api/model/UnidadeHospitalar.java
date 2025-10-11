package com.vidaplus.sghss_api.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "unidades_hospitalares")
public class UnidadeHospitalar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "É obrigatório informar o nome da unidade")
	@Column(name = "nome", nullable = false, unique = true)
	private String nome;
	
	@NotBlank(message = "É obrigatório informar o endereço da unidade")
	@Column(name = "endereco", nullable = false, unique = true)
	private String endereco;
	
	@JsonIgnore // Evita que as consultas sejam incluídas no JSON do paciente, prevenindo loops
	@OneToMany(mappedBy = "unidadeHospitalar")
	private List<Consulta> consultas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(consultas, endereco, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnidadeHospitalar other = (UnidadeHospitalar) obj;
		return Objects.equals(consultas, other.consultas) && Objects.equals(endereco, other.endereco)
				&& id == other.id && Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "UnidadeHospitalar [id=" + id + ", nome=" + nome + ", endereco=" + endereco
				+ ", consultas=" + consultas + "]";
	}
	
	
	
}
