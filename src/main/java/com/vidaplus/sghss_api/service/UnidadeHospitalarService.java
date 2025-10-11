package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vidaplus.sghss_api.dto.UnidadeHospitalarDTO;
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

    public Optional<UnidadeHospitalar> buscarPorId(Long id) {
        return unidadeRepository.findById(id);
    }

    public UnidadeHospitalar criarUnidade(UnidadeHospitalarDTO unidadeDTO) {
        if (unidadeRepository.findByNome(unidadeDTO.getNome()).isPresent()) {
            throw new RuntimeException("Já existe uma unidade hospitalar com este nome.");
        }

        UnidadeHospitalar novaUnidade = new UnidadeHospitalar();
        novaUnidade.setNome(unidadeDTO.getNome());
        novaUnidade.setEndereco(unidadeDTO.getEndereco());

        return unidadeRepository.save(novaUnidade);
    }

    public Optional<UnidadeHospitalar> atualizarUnidade(Long id, UnidadeHospitalarDTO unidadeDTO) {
        return unidadeRepository.findById(id)
            .map(unidadeExistente -> {
                unidadeRepository.findByNome(unidadeDTO.getNome()).ifPresent(unidadeComNome -> {
                    if (unidadeComNome.getId() != id) {
                        throw new RuntimeException("O nome '" + unidadeDTO.getNome() + "' já pertence a outra unidade.");
                    }
                });

                unidadeExistente.setNome(unidadeDTO.getNome());
                unidadeExistente.setEndereco(unidadeDTO.getEndereco());

                return unidadeRepository.save(unidadeExistente);
            });
    }

    public boolean deletarUnidade(Long id) {
        if (unidadeRepository.existsById(id)) {
            unidadeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}