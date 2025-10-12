package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.MedicoDTO;
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

    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> buscarPorId(Long id) {
        return medicoRepository.findById(id);
    }
    
    public Medico buscarMeuPerfil() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String emailUsuarioLogado;

        if (principal instanceof UserDetails) {
            emailUsuarioLogado = ((UserDetails) principal).getUsername();
        } else {
            emailUsuarioLogado = principal.toString();
        }

        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado no token."));

        return medicoRepository.findByUsuario_Id(usuario.getId())
            .orElseThrow(() -> new RuntimeException("Nenhum perfil de médico encontrado para o usuário logado."));
    }

    public Medico criarMedico(MedicoDTO medicoDTO) {
        if (medicoRepository.findByCrm(medicoDTO.getCrm()).isPresent()) {
            throw new RuntimeException("Este CRM já está cadastrado.");
        }

        if (medicoRepository.findByUsuario_Id(medicoDTO.getUsuarioId()).isPresent()) {
            throw new RuntimeException("Este ID de usuário já está associado a outro médico.");
        }        

        Usuario usuario = usuarioRepository.findById(medicoDTO.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + medicoDTO.getUsuarioId()));

        if (usuario.getTipoUsuario() != TipoUsuario.MEDICO) {
            // Se não for, lança uma exceção
            throw new IllegalArgumentException("Não é possível cadastrar um médico para um usuário que não seja do tipo MEDICO.");
        }

        Medico novoMedico = new Medico();
        
        novoMedico.setNome(medicoDTO.getNome());
        novoMedico.setCrm(medicoDTO.getCrm());
        novoMedico.setEspecialidade(medicoDTO.getEspecialidade());
        novoMedico.setUsuario(usuario);

        return medicoRepository.save(novoMedico);
    }

    public Optional<Medico> atualizarMedico(Long id, MedicoDTO medicoDTO) {
        return medicoRepository.findById(id)
            .map(medicoExistente -> {
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

                return medicoRepository.save(medicoExistente);
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