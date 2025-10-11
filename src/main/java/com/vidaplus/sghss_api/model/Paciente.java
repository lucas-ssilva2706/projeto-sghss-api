 package com.vidaplus.sghss_api.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pacientes")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPaciente;
	
	@NotBlank(message = "É obrigatório informar o nome do paciente")
	@Column(name="nome", nullable = false)
	private String nome;
	
	@NotBlank(message = "É obrigatório informar o CPF do paciente")
	@Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres")
	@Column(name="cpf", nullable=false, unique = true)
	private String cpf;

	@NotNull(message = "É obrigatório informar a Data de Nascimento do paciente")
	@Column(name="data_nascimento",nullable=false)
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate dataNascimento;
	
	@NotBlank(message = "É obrigatório informar o telefone do paciente")	
	@Column(name="fone", nullable=false)
	private String telefone;
	
	@NotNull(message = "É obrigatório informar a idUsuario")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario", referencedColumnName = "idUsuario")
	@JsonManagedReference("paciente-usuario")
	private Usuario usuario;
	
	@JsonIgnore
	@OneToMany(mappedBy = "paciente")
	private List<Consulta> consultas;
	
    @JsonIgnore
    @OneToOne(mappedBy = "paciente")
    @JsonManagedReference("paciente-prontuario")
    private Prontuario prontuario;

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Paciente [idPaciente=" + idPaciente + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento="
				+ dataNascimento + ", telefone=" + telefone + ", usuario=" + usuario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, dataNascimento, idPaciente, nome, telefone, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(dataNascimento, other.dataNascimento)
				&& Objects.equals(idPaciente, other.idPaciente) && Objects.equals(nome, other.nome)
				&& Objects.equals(telefone, other.telefone) && Objects.equals(usuario, other.usuario);
	}
	
	
}
