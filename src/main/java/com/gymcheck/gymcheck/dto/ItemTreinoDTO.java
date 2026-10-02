package com.gymcheck.gymcheck.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemTreinoDTO(
        @NotNull @Positive Integer series,
        @NotNull @Positive Integer repeticoes,
        @NotNull @DecimalMin("0.0") Double peso) {
}