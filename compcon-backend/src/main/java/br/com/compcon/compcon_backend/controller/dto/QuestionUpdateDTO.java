package br.com.compcon.compcon_backend.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


public record QuestionUpdateDTO(
        @NotBlank(message = "O enunciado não pode ser vazio.")
        String statement,

        @NotNull(message = "O ID da matéria é obrigatório.")
        Long subjectId,

        @NotNull(message = "O ID da fonte é obrigatório.")
        Long sourceId,

        @NotNull(message = "A lista de alternativas é obrigatória.")
        @Size(min = 2, message = "A questão deve ter pelo menos duas alternativas.")
        @Valid
        List<AlternativeCreateDTO> alternatives
) {
}
