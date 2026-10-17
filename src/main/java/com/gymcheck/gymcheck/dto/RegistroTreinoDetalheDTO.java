package com.gymcheck.gymcheck.dto;

import java.time.LocalDate;
import java.util.List;

public record RegistroTreinoDetalheDTO(
        LocalDate data,
        String rotinaNome,
        String observacoesDia,
        List<ExercicioRegistradoDTO> exercicios) {

    public record ExercicioRegistradoDTO(String nome, List<SerieRegistradaDTO> series) {}

    public record SerieRegistradaDTO(Integer numeroSerie, Integer repeticoes, Double pesoKg) {}
}