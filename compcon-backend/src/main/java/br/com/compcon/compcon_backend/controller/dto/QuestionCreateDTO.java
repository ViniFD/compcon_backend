package br.com.compcon.compcon_backend.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


public record QuestionCreateDTO(
        @NotBlank(message = "O enunciado da questão é obrigatório.")
        String statement,

        @NotNull(message = "O ID da matéria é obrigatório.")
        Long subjectId,

        @NotNull(message = "O ID da fonte é obrigatório.")
        Long sourceId,

        @Valid
        @NotNull
        @Size(min = 2, message = "Uma questão deve ter no mínimo 2 alternativas.")
        List<AlternativeCreateDTO> alternatives
) {
}
