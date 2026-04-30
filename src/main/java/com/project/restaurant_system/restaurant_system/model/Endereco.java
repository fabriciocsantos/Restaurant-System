package com.project.restaurant_system.restaurant_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Endereco {

    @Column(nullable = false, length = 150)
    private String rua;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(length = 120)
    private String complemento;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 10)
    private String cep;

    public Endereco(String rua, String numero, String complemento, String cidade, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.cep = cep;
    }
}
