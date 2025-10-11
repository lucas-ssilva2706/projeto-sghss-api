package com.vidaplus.sghss_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vidaplus.sghss_api.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Optional<Medico> findByCrm(String crm);
    
}