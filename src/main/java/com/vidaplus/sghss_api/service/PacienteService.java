package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.PacienteDTO;
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

	public List<Paciente> listarTodos() {
		return pacienteRepository.findAll();
	}

	public Optional<Paciente> buscarPorId(Long id) {
		return pacienteRepository.findById(id);
	}

    public Paciente criarPaciente(PacienteDTO pacienteDTO) {
        if (pacienteRepository.findByCpf(pacienteDTO.getCpf()).isPresent()) {
            throw new RuntimeException("Este CPF já está cadastrado.");
        }

        if (pacienteRepository.findByUsuario_Id(pacienteDTO.getUsuarioId()).isPresent()) {
            throw new RuntimeException("Este ID de usuário já está associado a outro paciente.");
        }
        
        Usuario usuario = usuarioRepository.findById(pacienteDTO.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + pacienteDTO.getUsuarioId()));

        if (usuario.getTipoUsuario() != TipoUsuario.PACIENTE) {
            throw new IllegalArgumentException("Não é possível cadastrar um paciente para um usuário que não seja do tipo PACIENTE.");
        }      

        // Cria e salva a nova instância do Paciente
        Paciente novoPaciente = new Paciente();
        novoPaciente.setNome(pacienteDTO.getNome());
        novoPaciente.setCpf(pacienteDTO.getCpf());
        novoPaciente.setDataNascimento(pacienteDTO.getDataNascimento());
        novoPaciente.setTelefone(pacienteDTO.getTelefone());
        novoPaciente.setUsuario(usuario);

        return pacienteRepository.save(novoPaciente);
    }

	public Optional<Paciente> atualizarPaciente(Long id, PacienteDTO pacienteDTO) {

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

			return pacienteRepository.save(pacienteExistente);
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