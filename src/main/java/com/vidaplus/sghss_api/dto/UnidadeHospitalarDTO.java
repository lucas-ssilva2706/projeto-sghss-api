package com.vidaplus.sghss_api.dto;

import jakarta.validation.constraints.NotBlank;

public class UnidadeHospitalarDTO {

    @NotBlank(message = "O nome não pode estar em branco.")
    private String nome;

    @NotBlank(message = "O endereço não pode estar em branco.")
    private String endereco;

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
    
}