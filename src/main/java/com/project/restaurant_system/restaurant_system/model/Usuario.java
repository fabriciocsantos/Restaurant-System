package com.project.restaurant_system.restaurant_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_usuario_login", columnNames = "login")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 50)
    private String login;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false)
    private LocalDateTime dataUltimaAlteracao;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipoUsuario;

    public Usuario(String nome, String email, String login, String senha, Endereco endereco, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
        this.tipoUsuario = tipoUsuario;
    }

    public void atualizarDados(
            String nome,
            String email,
            String login,
            Endereco endereco,
            TipoUsuario tipoUsuario
    ) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.endereco = endereco;
        this.tipoUsuario = tipoUsuario;
    }

    public void atualizarSenha(String senha) {
        this.senha = senha;
    }

    @PrePersist
    @PreUpdate
    public void atualizarDataUltimaAlteracao() {
        this.dataUltimaAlteracao = LocalDateTime.now();
    }
}
