package com.gymcheck.gymcheck.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SessaoTreinoForm {
    private String observacoesDia;
    private List<ExercicioSessaoForm> exercicios = new ArrayList<>();

    @Data
    @NoArgsConstructor
    public static class ExercicioSessaoForm {
        private Long itemTreinoId;
        private List<SerieSessaoForm> series = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    public static class SerieSessaoForm {
        private Double pesoKg;
        private Integer repeticoes;
        private Boolean realizada;
    }
}