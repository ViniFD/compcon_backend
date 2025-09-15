package br.com.compcon.compcon_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record SubjectDTO(
        @NotBlank(message = "O nome da matéria é obrigatório.")
        @Size(max = 100, message = "O nome da matéria não pode exceder 100 caracteres.")
        String name
) {
}