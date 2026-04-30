package com.project.restaurant_system.restaurant_system.usuario.dto;

import com.project.restaurant_system.restaurant_system.model.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioAtualizacaoRequest(
        @NotBlank(message = "Nome e obrigatorio")
        @Size(max = 120, message = "Nome deve ter no maximo 120 caracteres")
        String nome,

        @NotBlank(message = "E-mail e obrigatorio")
        @Email(message = "E-mail invalido")
        @Size(max = 150, message = "E-mail deve ter no maximo 150 caracteres")
        String email,

        @NotBlank(message = "Login e obrigatorio")
        @Size(max = 50, message = "Login deve ter no maximo 50 caracteres")
        String login,

        @NotNull(message = "Tipo de usuario e obrigatorio")
        TipoUsuario tipoUsuario,

        @Valid
        @NotNull(message = "Endereco e obrigatorio")
        EnderecoRequest endereco
) {
}
