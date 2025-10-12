package com.vidaplus.sghss_api.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vidaplus.sghss_api.model.enums.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "É obrigatório informar o email")
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotBlank(message = "É obrigatório informar a senha")
	@Column(name = "senha", nullable = false)
	private String senha;

	@NotNull(message = "É obrigatório informar o tipo do usuário")
	@Column(name = "tipo_usuario", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	@OneToOne(mappedBy = "usuario")
	@JsonBackReference("paciente-usuario")
	private Paciente paciente;

	@OneToOne(mappedBy = "usuario")
	@JsonBackReference("medico-usuario")
	private Medico medico;

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

	public Long getId() {
		return id;
	}

	public void setId(Long idUsuario) {
		this.id = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", senha=" + senha + ", tipoUsuario=" + tipoUsuario
				+ ", paciente=" + paciente + ", medico=" + medico + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, medico, paciente, senha, tipoUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(medico, other.medico)
				&& Objects.equals(paciente, other.paciente) && Objects.equals(senha, other.senha)
				&& tipoUsuario == other.tipoUsuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(tipoUsuario.name()));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
