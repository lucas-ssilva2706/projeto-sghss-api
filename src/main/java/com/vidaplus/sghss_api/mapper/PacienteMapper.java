package com.vidaplus.sghss_api.mapper;

import com.vidaplus.sghss_api.dto.PacienteResponseDTO;
import com.vidaplus.sghss_api.dto.UsuarioResponseDTO;
import com.vidaplus.sghss_api.model.Paciente;
import com.vidaplus.sghss_api.model.Usuario;

public class PacienteMapper {

	public static PacienteResponseDTO toDTO(Paciente paciente) {
		if (paciente == null) {
			return null;
		}

		PacienteResponseDTO dto = new PacienteResponseDTO();
		dto.setId(paciente.getId());
		dto.setNome(paciente.getNome());
		dto.setCpf(paciente.getCpf());
		dto.setDataNascimento(paciente.getDataNascimento());
		dto.setTelefone(paciente.getTelefone());

		dto.setUsuario(toUsuarioDTO(paciente.getUsuario()));

		return dto;
	}

	private static UsuarioResponseDTO toUsuarioDTO(Usuario usuario) {
		if (usuario == null) {
			return null;
		}

		UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO();
		usuarioDTO.setId(usuario.getId());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setTipoUsuario(usuario.getTipoUsuario());

		return usuarioDTO;
	}
}