package com.example.seudestino.seudestino.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViagemRecordDto(@NotBlank String titulo, @NotBlank LocalDate dataInicio, @NotBlank LocalDate dataFim, @NotNull long DestinoId) {
}
