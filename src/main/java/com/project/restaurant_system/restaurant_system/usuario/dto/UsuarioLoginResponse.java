package com.project.restaurant_system.restaurant_system.usuario.dto;

import com.project.restaurant_system.restaurant_system.model.TipoUsuario;

public record UsuarioLoginResponse(
        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipoUsuario
) {
}
