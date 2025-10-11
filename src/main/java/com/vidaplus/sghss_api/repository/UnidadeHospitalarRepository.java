package com.vidaplus.sghss_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vidaplus.sghss_api.model.UnidadeHospitalar;

public interface UnidadeHospitalarRepository extends JpaRepository<UnidadeHospitalar, Long> {

    Optional<UnidadeHospitalar> findByNome(String nome);
    
}