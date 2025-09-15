package br.com.compcon.compcon_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record SourceDTO(
        @NotBlank(message = "O nome da fonte é obrigatório.")
        @Size(max = 150, message = "O nome da fonte não pode exceder 150 caracteres.")
        String name,

        @Size(max = 100, message = "O nome da instituição não pode exceder 100 caracteres.")
        String institution
) {
}
