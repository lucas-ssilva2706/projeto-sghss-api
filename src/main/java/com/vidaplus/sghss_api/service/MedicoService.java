package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.MedicoDTO;
import com.vidaplus.sghss_api.dto.MedicoResponseDTO;
import com.vidaplus.sghss_api.mapper.MedicoMapper;
import com.vidaplus.sghss_api.model.Medico;
import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.model.enums.TipoUsuario;
import com.vidaplus.sghss_api.repository.MedicoRepository;
import com.vidaplus.sghss_api.repository.UsuarioRepository;

@Service
public class MedicoService {

	private final MedicoRepository medicoRepository;
	private final UsuarioRepository usuarioRepository;

	public MedicoService(MedicoRepository medicoRepository, UsuarioRepository usuarioRepository) {
		this.medicoRepository = medicoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public List<MedicoResponseDTO> listarTodos() {
		return medicoRepository.findAll().stream().map(MedicoMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<MedicoResponseDTO> buscarPorId(long id) {
		return medicoRepository.findById(id).map(MedicoMapper::toDTO);
	}

	public MedicoResponseDTO buscarMeuPerfil() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailUsuarioLogado = ((UserDetails) principal).getUsername();

		Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado no token."));

		Medico medico = medicoRepository.findByUsuario_Id(usuario.getId())
				.orElseThrow(() -> new RuntimeException("Nenhum perfil de médico encontrado para o usuário logado."));

		return MedicoMapper.toDTO(medico);
	}

	public MedicoResponseDTO criarMedico(MedicoDTO medicoDTO) {
		if (medicoRepository.findByCrm(medicoDTO.getCrm()).isPresent()) {
			throw new RuntimeException("Este CRM já está cadastrado.");
		}

		if (medicoRepository.findByUsuario_Id(medicoDTO.getUsuarioId()).isPresent()) {
			throw new RuntimeException("Este ID de usuário já está associado a outro médico.");
		}

		Usuario usuario = usuarioRepository.findById(medicoDTO.getUsuarioId()).orElseThrow(
				() -> new RuntimeException("Usuário não encontrado com o ID: " + medicoDTO.getUsuarioId()));

		if (usuario.getTipoUsuario() != TipoUsuario.MEDICO) {
			throw new IllegalArgumentException(
					"Não é possível cadastrar um médico para um usuário que não seja do tipo MEDICO.");
		}

		Medico novoMedico = new Medico();

		novoMedico.setNome(medicoDTO.getNome());
		novoMedico.setCrm(medicoDTO.getCrm());
		novoMedico.setEspecialidade(medicoDTO.getEspecialidade());
		novoMedico.setUsuario(usuario);

		Medico medicoSalvo = medicoRepository.save(novoMedico);

		return MedicoMapper.toDTO(medicoSalvo);
	}

	public Optional<MedicoResponseDTO> atualizarMedico(Long id, MedicoDTO medicoDTO) {
		return medicoRepository.findById(id).map(medicoExistente -> {
			if (!medicoExistente.getUsuario().getId().equals(medicoDTO.getUsuarioId())) {
				throw new IllegalArgumentException("Não é permitido alterar o usuário associado a um médico.");
			}

			medicoRepository.findByCrm(medicoDTO.getCrm()).ifPresent(medicoComCrm -> {
				if (!medicoComCrm.getId().equals(id)) {
					throw new RuntimeException("O CRM " + medicoDTO.getCrm() + " já pertence a outro médico.");
				}
			});

			medicoExistente.setNome(medicoDTO.getNome());
			medicoExistente.setCrm(medicoDTO.getCrm());
			medicoExistente.setEspecialidade(medicoDTO.getEspecialidade());

			Medico medicoSalvo = medicoRepository.save(medicoExistente);

			return MedicoMapper.toDTO(medicoSalvo);
		});
	}

	public boolean deletarMedico(Long id) {
		if (medicoRepository.existsById(id)) {
			medicoRepository.deleteById(id);
			return true;
		}
		return false;
	}
}