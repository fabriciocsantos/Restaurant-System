package com.project.restaurant_system.restaurant_system.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String login;

    private String senha;

    private LocalDateTime dataUltimaAlteracao;

    private String endereco;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}