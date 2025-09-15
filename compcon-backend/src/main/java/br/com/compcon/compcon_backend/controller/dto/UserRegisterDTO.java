package br.com.compcon.compcon_backend.controller.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRegisterDTO(
        @NotBlank(message = "O nome de utilizador é obrigatório")
        @Size(min = 3, max = 50, message = "O nome de utilizador deve ter entre 3 e 50 caracteres")
        String username,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O formato do email é inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password
) {
}