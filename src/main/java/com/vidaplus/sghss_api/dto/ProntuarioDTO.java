package com.vidaplus.sghss_api.dto;

import jakarta.validation.constraints.NotNull;

public class ProntuarioDTO {

    @NotNull(message = "O ID do paciente é obrigatório.")
    private Long pacienteId;

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}