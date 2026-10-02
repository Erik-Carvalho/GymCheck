package com.gymcheck.gymcheck.controller;

import com.gymcheck.gymcheck.dto.ItemTreinoDTO;
import com.gymcheck.gymcheck.model.ItemTreino;
import com.gymcheck.gymcheck.service.TreinoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercicios")
public class ItemTreinoController {

    private final TreinoService treinoService;

    public ItemTreinoController(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @PutMapping("/{id}")
    public ItemTreinoDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemTreinoDTO dados) {
        ItemTreino itemAtualizado = treinoService.atualizarItemTreino(id, dados);
        return new ItemTreinoDTO(itemAtualizado.getSeries(), itemAtualizado.getRepeticoes(), itemAtualizado.getPeso());
    }
}