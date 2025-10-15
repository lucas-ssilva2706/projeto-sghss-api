package com.vidaplus.sghss_api.mapper;

import com.vidaplus.sghss_api.dto.MedicoResponseDTO;
import com.vidaplus.sghss_api.dto.UsuarioResponseDTO;
import com.vidaplus.sghss_api.model.Medico;
import com.vidaplus.sghss_api.model.Usuario;

public class MedicoMapper {

	public static MedicoResponseDTO toDTO(Medico medico) {
		if (medico == null) {
			return null;
		}

		MedicoResponseDTO dto = new MedicoResponseDTO();
		dto.setId(medico.getId());
		dto.setNome(medico.getNome());
		dto.setCrm(medico.getCrm());
		dto.setEspecialidade(medico.getEspecialidade());

		dto.setUsuario(toUsuarioDTO(medico.getUsuario()));

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