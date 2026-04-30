package com.project.restaurant_system.restaurant_system.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginRequest(
        @NotBlank(message = "Login e obrigatorio")
        String login,

        @NotBlank(message = "Senha e obrigatoria")
        String senha
) {
}
