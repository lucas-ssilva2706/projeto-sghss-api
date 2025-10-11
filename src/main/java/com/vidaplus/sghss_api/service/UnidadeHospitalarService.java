package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vidaplus.sghss_api.model.UnidadeHospitalar;
import com.vidaplus.sghss_api.repository.UnidadeHospitalarRepository;

@Service
public class UnidadeHospitalarService {

    private final UnidadeHospitalarRepository unidadeRepository;

    public UnidadeHospitalarService(UnidadeHospitalarRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public List<UnidadeHospitalar> listarTodas() {
        return unidadeRepository.findAll();
    }

    public Optional<UnidadeHospitalar> buscarPorId(long id) {
        return unidadeRepository.findById(id);
    }

    public UnidadeHospitalar criarUnidade(UnidadeHospitalar unidade) {
        if (unidadeRepository.findByNome(unidade.getNome()).isPresent()) {
            throw new RuntimeException("Já existe uma unidade hospitalar com este nome.");
        }
        return unidadeRepository.save(unidade);
    }

    public Optional<UnidadeHospitalar> atualizarUnidade(long id, UnidadeHospitalar unidadeOld) {
        return unidadeRepository.findById(id)
            .map(unidade -> {
                unidadeRepository.findByNome(unidadeOld.getNome()).ifPresent(unidadeComNome -> {
                    if (unidadeComNome.getIdUnidade() != id) {
                        throw new RuntimeException("O nome '" + unidadeOld.getNome() + "' já pertence a outra unidade.");
                    }
                });

                // Atualiza os dados da unidade existente
                unidade.setNome(unidadeOld.getNome());
                unidade.setEndereco(unidadeOld.getEndereco());

                return unidadeRepository.save(unidade);
            });
    }

    public boolean deletarUnidade(long id) {
        if (unidadeRepository.existsById(id)) {
            unidadeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}