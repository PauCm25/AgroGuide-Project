package com.agroguide.auth.infraestructure.driver_adapters.jpa_repository;


import com.agroguide.auth.infraestructure.driver_adapters.UsuarioData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioDataJpaRepository extends JpaRepository<UsuarioData,Long> {
    Optional<UsuarioData> findByEmail(String email);
}
