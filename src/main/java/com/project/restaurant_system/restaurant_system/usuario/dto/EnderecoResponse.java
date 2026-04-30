package com.project.restaurant_system.restaurant_system.usuario.dto;

public record EnderecoResponse(
        String rua,
        String numero,
        String complemento,
        String cidade,
        String cep
) {
}
