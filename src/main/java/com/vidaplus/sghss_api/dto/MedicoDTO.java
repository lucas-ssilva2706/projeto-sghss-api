package com.vidaplus.sghss_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicoDTO {

	@NotBlank(message = "O nome não pode estar em branco.")
	private String nome;

	@NotBlank(message = "O CRM não pode estar em branco.")
	private String crm;

	@NotBlank(message = "A especialidade não pode estar em branco.")
	private String especialidade;

	@NotNull(message = "O ID do usuário é obrigatório.")
	private Long usuarioId;

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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

}