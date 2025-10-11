package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está em uso.");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> atualizarUsuario(long id, Usuario usuarioParaAtualizar) {
        return usuarioRepository.findById(id)
            .map(usuarioExistente -> {
                usuarioExistente.setEmail(usuarioParaAtualizar.getEmail());
                usuarioExistente.setSenha(usuarioParaAtualizar.getSenha());
                usuarioExistente.setTipoUsuario(usuarioParaAtualizar.getTipoUsuario());
                return usuarioRepository.save(usuarioExistente);
            });
    }

    public boolean deletarUsuario(long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}