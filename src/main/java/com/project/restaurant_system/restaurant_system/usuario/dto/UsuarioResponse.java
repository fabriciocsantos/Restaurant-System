package com.project.restaurant_system.restaurant_system.usuario.dto;

import com.project.restaurant_system.restaurant_system.model.TipoUsuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String login,
        LocalDateTime dataUltimaAlteracao,
        EnderecoResponse endereco,
        TipoUsuario tipoUsuario
) {
}
