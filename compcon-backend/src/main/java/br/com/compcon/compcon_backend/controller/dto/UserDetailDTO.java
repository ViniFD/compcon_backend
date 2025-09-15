package br.com.compcon.compcon_backend.controller.dto;

import br.com.compcon.compcon_backend.model.user.User;

import java.util.Set;
import java.util.stream.Collectors;


public record UserDetailDTO(
        Long id,
        String username,
        String email,
        Set<String> roles
) {
    public UserDetailDTO(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );
    }
}