package com.project.restaurant_system.restaurant_system.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioSenhaAtualizacaoRequest(
        @NotBlank(message = "Nova senha e obrigatoria")
        @Size(min = 8, max = 100, message = "Nova senha deve ter entre 8 e 100 caracteres")
        String novaSenha
) {
}
