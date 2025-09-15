package br.com.compcon.compcon_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AlternativeCreateDTO(
        @NotBlank(message = "O texto da alternativa não pode ser vazio.")
        String text,

        @NotNull(message = "É necessário indicar se a alternativa é a correta.")
        boolean isCorrect
) {
}