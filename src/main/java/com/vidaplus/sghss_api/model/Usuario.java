package com.vidaplus.sghss_api.model;

import java.util.Objects;

import com.vidaplus.sghss_api.model.enums.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idUsuario;
	
	@NotBlank(message = "É obrigatório informar o email")
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "É obrigatório informar a senha")
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@NotBlank(message = "É obrigatório informar o tipo do usuário")
	@Column(name = "tipo_usuario", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
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

	public @NotBlank(message = "É obrigatório informar o tipo do usuário") TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(@NotBlank(message = "É obrigatório informar o tipo do usuário") TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", email=" + email + ", senha=" + senha + ", tipoUsuario="
				+ tipoUsuario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, idUsuario, senha, tipoUsuario);
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
		return Objects.equals(email, other.email) && idUsuario == other.idUsuario && Objects.equals(senha, other.senha)
				&& Objects.equals(tipoUsuario, other.tipoUsuario);
	}
	
	
}
