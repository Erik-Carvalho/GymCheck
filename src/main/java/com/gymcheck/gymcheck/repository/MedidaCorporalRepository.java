package com.gymcheck.gymcheck.repository;

import com.gymcheck.gymcheck.model.MedidaCorporal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedidaCorporalRepository extends JpaRepository<MedidaCorporal, Long> {
    List<MedidaCorporal> findByUsuarioIdOrderByDataDesc(Long usuarioId);
    Optional<MedidaCorporal> findFirstByUsuarioIdOrderByDataDesc(Long usuarioId);
    Optional<MedidaCorporal> findByIdAndUsuarioId(Long id, Long usuarioId);
    List<MedidaCorporal> findAllByOrderByDataDesc();
    Optional<MedidaCorporal> findFirstByOrderByDataDesc();
}