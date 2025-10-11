package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vidaplus.sghss_api.model.Medico;
import com.vidaplus.sghss_api.repository.MedicoRepository;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> buscarPorId(long id) {
        return medicoRepository.findById(id);
    }

    public Medico criarMedico(Medico medico) {
        if (medicoRepository.findByCrm(medico.getCrm()).isPresent()) {
            throw new RuntimeException("Este CRM já está cadastrado.");
        }
        return medicoRepository.save(medico);
    }

    public Optional<Medico> atualizarMedico(long id, Medico medicoOld) {
        return medicoRepository.findById(id)
            .map(medico -> {
                // REGRA DE NEGÓCIO: Verificar se o novo CRM já pertence a outro médico
                medicoRepository.findByCrm(medicoOld.getCrm()).ifPresent(medicoComCrm -> {
                    if (medicoComCrm.getIdMedico() != id) {
                        throw new RuntimeException("O CRM " + medicoOld.getCrm() + " já pertence a outro médico.");
                    }
                });

                medico.setNome(medicoOld.getNome());
                medico.setCrm(medicoOld.getCrm());
                medico.setEspecialidade(medicoOld.getEspecialidade());

                return medicoRepository.save(medico);
            });
    }

    public boolean deletarMedico(long id) {
        if (medicoRepository.existsById(id)) {
            medicoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}