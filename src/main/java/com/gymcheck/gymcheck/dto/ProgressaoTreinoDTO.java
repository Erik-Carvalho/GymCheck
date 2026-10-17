package com.gymcheck.gymcheck.dto;

import java.util.List;
import java.util.Map;

public record ProgressaoTreinoDTO(List<String> datas, Map<String, List<Double>> cargasPorExercicio) {
}