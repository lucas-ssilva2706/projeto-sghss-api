package com.vidaplus.sghss_api.mapper;

import com.vidaplus.sghss_api.dto.UsuarioResponseDTO;
import com.vidaplus.sghss_api.model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setTipoUsuario(usuario.getTipoUsuario());

        return dto;
    }
}