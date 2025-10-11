package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vidaplus.sghss_api.dto.UsuarioDTO;
import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está em uso.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(usuarioDTO.getEmail());
        novoUsuario.setSenha(usuarioDTO.getSenha());
        novoUsuario.setTipoUsuario(usuarioDTO.getTipoUsuario());

        return usuarioRepository.save(novoUsuario);
    }

    public Optional<Usuario> atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id)
            .map(usuarioExistente -> {
                usuarioRepository.findByEmail(usuarioDTO.getEmail()).ifPresent(usuarioComEmail -> {
                    if (usuarioComEmail.getId() != id) {
                        throw new RuntimeException("O e-mail " + usuarioDTO.getEmail() + " já pertence a outro usuário.");
                    }
                });

                usuarioExistente.setEmail(usuarioDTO.getEmail());
                usuarioExistente.setSenha(usuarioDTO.getSenha());
                usuarioExistente.setTipoUsuario(usuarioDTO.getTipoUsuario());

                return usuarioRepository.save(usuarioExistente);
            });
    }

    public boolean deletarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}