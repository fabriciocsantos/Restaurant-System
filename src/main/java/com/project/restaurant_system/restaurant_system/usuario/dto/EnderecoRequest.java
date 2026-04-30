package com.project.restaurant_system.restaurant_system.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(
        @NotBlank(message = "Rua é obrigatoria")
        @Size(max = 150, message = "Rua deve ter no maximo 150 caracteres")
        String rua,

        @NotBlank(message = "Numero é obrigatorio")
        @Size(max = 20, message = "Numero deve ter no maximo 20 caracteres")
        String numero,

        @Size(max = 120, message = "Complemento deve ter no maximo 120 caracteres")
        String complemento,

        @NotBlank(message = "Cidade e obrigatoria")
        @Size(max = 100, message = "Cidade deve ter no maximo 100 caracteres")
        String cidade,

        @NotBlank(message = "CEP e obrigatorio")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve estar no formato 00000-000")
        String cep
) {
}
