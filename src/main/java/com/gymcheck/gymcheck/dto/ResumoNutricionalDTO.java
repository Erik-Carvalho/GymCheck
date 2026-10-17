package com.gymcheck.gymcheck.dto;

public record ResumoNutricionalDTO(
        double calorias,
        double proteinasGramas,
        double carboidratosGramas,
        double gordurasGramas,
        Integer metaCalorias,
        Integer metaProteinas,
        Integer metaCarboidratos,
        Integer metaGorduras,
        int progressoCalorias,
        int progressoProteinas,
        int progressoCarboidratos,
        int progressoGorduras,
        boolean temDadosNutricionais) {
}