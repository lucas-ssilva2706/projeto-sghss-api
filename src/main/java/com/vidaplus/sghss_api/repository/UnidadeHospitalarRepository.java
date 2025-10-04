package com.vidaplus.sghss_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vidaplus.sghss_api.model.UnidadeHospitalar;

@Repository
public interface UnidadeHospitalarRepository extends JpaRepository<UnidadeHospitalar, Long>{

}
