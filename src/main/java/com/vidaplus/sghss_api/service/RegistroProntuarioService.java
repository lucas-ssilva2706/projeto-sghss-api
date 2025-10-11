package com.vidaplus.sghss_api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vidaplus.sghss_api.model.RegistroProntuario;
import com.vidaplus.sghss_api.repository.RegistroProntuarioRepository;

@Service
public class RegistroProntuarioService {

    private final RegistroProntuarioRepository registroRepository;

    public RegistroProntuarioService(RegistroProntuarioRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public List<RegistroProntuario> listarTodos() {
        return registroRepository.findAll();
    }

    public Optional<RegistroProntuario> buscarPorId(long id) {
        return registroRepository.findById(id);
    }

    public RegistroProntuario criarRegistro(RegistroProntuario registro) {
        if (registro.getProntuario() == null || registro.getProntuario().getIdProntuario() == null) {
            throw new IllegalArgumentException("O registro deve estar associado a um prontuário válido.");
        }
        return registroRepository.save(registro);
    }

    public boolean deletarRegistro(long id) {
        if (registroRepository.existsById(id)) {
            registroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}