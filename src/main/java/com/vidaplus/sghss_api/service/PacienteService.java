package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.PacienteDTO;
import com.vidaplus.sghss_api.dto.PacienteResponseDTO;
import com.vidaplus.sghss_api.mapper.PacienteMapper;
import com.vidaplus.sghss_api.model.Paciente;
import com.vidaplus.sghss_api.model.Usuario;
import com.vidaplus.sghss_api.model.enums.TipoUsuario;
import com.vidaplus.sghss_api.repository.PacienteRepository;
import com.vidaplus.sghss_api.repository.UsuarioRepository;

@Service
public class PacienteService {

	private final PacienteRepository pacienteRepository;
	private final UsuarioRepository usuarioRepository;

	public PacienteService(PacienteRepository pacienteRepository, UsuarioRepository usuarioRepository) {
		this.pacienteRepository = pacienteRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public List<PacienteResponseDTO> listarTodos() {
		return pacienteRepository.findAll().stream().map(PacienteMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<PacienteResponseDTO> buscarPorId(long id) {
		return pacienteRepository.findById(id).map(PacienteMapper::toDTO);
	}

	public PacienteResponseDTO criarPaciente(PacienteDTO pacienteDTO) {
		if (pacienteRepository.findByCpf(pacienteDTO.getCpf()).isPresent()) {
			throw new RuntimeException("Este CPF já está cadastrado.");
		}

		if (pacienteRepository.findByUsuario_Id(pacienteDTO.getUsuarioId()).isPresent()) {
			throw new RuntimeException("Este ID de usuário já está associado a outro paciente.");
		}

		Usuario usuario = usuarioRepository.findById(pacienteDTO.getUsuarioId()).orElseThrow(
				() -> new RuntimeException("Usuário não encontrado com o ID: " + pacienteDTO.getUsuarioId()));

		if (usuario.getTipoUsuario() != TipoUsuario.PACIENTE) {
			throw new IllegalArgumentException(
					"Não é possível cadastrar um paciente para um usuário que não seja do tipo PACIENTE.");
		}

		Paciente novoPaciente = new Paciente();
		novoPaciente.setNome(pacienteDTO.getNome());
		novoPaciente.setCpf(pacienteDTO.getCpf());
		novoPaciente.setDataNascimento(pacienteDTO.getDataNascimento());
		novoPaciente.setTelefone(pacienteDTO.getTelefone());
		novoPaciente.setUsuario(usuario);
		
		Paciente pacienteSalvo = pacienteRepository.save(novoPaciente);

		return PacienteMapper.toDTO(pacienteSalvo);
	}

	public PacienteResponseDTO buscarMeuPerfil() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String emailUsuarioLogado = ((UserDetails) principal).getUsername();

		Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado no token."));

		Paciente paciente = pacienteRepository.findByUsuario_Id(usuario.getId())
				.orElseThrow(() -> new RuntimeException("Nenhum perfil de paciente encontrado para o usuário logado."));

		return PacienteMapper.toDTO(paciente);
	}

	public Optional<PacienteResponseDTO> atualizarPaciente(Long id, PacienteDTO pacienteDTO) {

		return pacienteRepository.findById(id).map(pacienteExistente -> {

			if (!pacienteExistente.getUsuario().getId().equals(pacienteDTO.getUsuarioId())) {
				throw new IllegalArgumentException("Não é permitido alterar o usuário associado a um paciente.");
			}

			pacienteRepository.findByCpf(pacienteDTO.getCpf()).ifPresent(pacienteComCpf -> {
				if (!pacienteComCpf.getId().equals(id)) {
					throw new RuntimeException("O CPF " + pacienteDTO.getCpf() + " já pertence a outro paciente.");
				}
			});

			pacienteExistente.setNome(pacienteDTO.getNome());
			pacienteExistente.setCpf(pacienteDTO.getCpf());
			pacienteExistente.setDataNascimento(pacienteDTO.getDataNascimento());
			pacienteExistente.setTelefone(pacienteDTO.getTelefone());

			Paciente pacienteSalvo = pacienteRepository.save(pacienteExistente);
			
			return PacienteMapper.toDTO(pacienteSalvo);
		});
	}

	public boolean deletarPaciente(Long id) {
		if (pacienteRepository.existsById(id)) {
			pacienteRepository.deleteById(id);
			return true;
		}
		return false;
	}
}