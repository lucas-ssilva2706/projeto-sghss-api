package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.UsuarioDTO;
import com.vidaplus.sghss_api.dto.UsuarioResponseDTO;
import com.vidaplus.sghss_api.mapper.UsuarioMapper;
import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UsuarioResponseDTO> listarTodos() {
		return usuarioRepository.findAll().stream().map(UsuarioMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<UsuarioResponseDTO> buscarPorId(long id) {
		return usuarioRepository.findById(id).map(UsuarioMapper::toDTO);
	}

	public UsuarioResponseDTO criarUsuario(UsuarioDTO usuarioDTO) {
		if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
			throw new RuntimeException("Este e-mail já está em uso.");
		}

		Usuario novoUsuario = new Usuario();
		novoUsuario.setEmail(usuarioDTO.getEmail());
		novoUsuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
		novoUsuario.setTipoUsuario(usuarioDTO.getTipoUsuario());

		Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

		return UsuarioMapper.toDTO(usuarioSalvo);
	}

	public Optional<UsuarioResponseDTO> atualizarUsuario(long id, UsuarioDTO usuarioDTO) {
		return usuarioRepository.findById(id).map(usuarioExistente -> {
			usuarioRepository.findByEmail(usuarioDTO.getEmail()).ifPresent(usuarioComEmail -> {
				if (usuarioComEmail.getId() != id) {
					throw new RuntimeException("O e-mail " + usuarioDTO.getEmail() + " já pertence a outro usuário.");
				}
			});

			usuarioExistente.setEmail(usuarioDTO.getEmail());
			usuarioExistente.setTipoUsuario(usuarioDTO.getTipoUsuario());

			if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
				usuarioExistente.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
			}

			Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

			return UsuarioMapper.toDTO(usuarioAtualizado);
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