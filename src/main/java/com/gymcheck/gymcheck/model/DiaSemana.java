package com.gymcheck.gymcheck.model;

import java.time.DayOfWeek;

public enum DiaSemana {
    SEGUNDA("Segunda-feira", DayOfWeek.MONDAY),
    TERCA("Terça-feira", DayOfWeek.TUESDAY),
    QUARTA("Quarta-feira", DayOfWeek.WEDNESDAY),
    QUINTA("Quinta-feira", DayOfWeek.THURSDAY),
    SEXTA("Sexta-feira", DayOfWeek.FRIDAY),
    SABADO("Sábado", DayOfWeek.SATURDAY),
    DOMINGO("Domingo", DayOfWeek.SUNDAY);

    private final String descricao;
    private final DayOfWeek dayOfWeek;

    DiaSemana(String descricao, DayOfWeek dayOfWeek) {
        this.descricao = descricao;
        this.dayOfWeek = dayOfWeek;
    }

    public String getDescricao() {
        return descricao;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public static DiaSemana fromDayOfWeek(DayOfWeek dayOfWeek) {
        for (DiaSemana dia : values()) {
            if (dia.dayOfWeek == dayOfWeek) {
                return dia;
            }
        }
        return null;
    }
}
