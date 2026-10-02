package com.gymcheck.gymcheck.repository;

import com.gymcheck.gymcheck.model.ItemTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemTreinoRepository extends JpaRepository<ItemTreino, Long> {
    List<ItemTreino> findByRotinaTreinoIdOrderByOrdemAscIdAsc(Long rotinaId);
    Optional<ItemTreino> findByIdAndRotinaTreinoUsuarioId(Long id, Long usuarioId);
}