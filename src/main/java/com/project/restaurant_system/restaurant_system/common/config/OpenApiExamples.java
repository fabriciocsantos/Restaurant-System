package com.project.restaurant_system.restaurant_system.common.config;

public final class OpenApiExamples {

    public static final String USUARIO_CRIACAO_REQUEST = """
            {
              "nome": "Maria Oliveira",
              "email": "maria@restaurante.com",
              "login": "maria.oliveira",
              "senha": "SenhaSegura123",
              "tipoUsuario": "DONO_RESTAURANTE",
              "endereco": {
                "rua": "Rua das Flores",
                "numero": "120",
                "complemento": "Sala 2",
                "cidade": "Sao Paulo",
                "cep": "01001-000"
              }
            }
            """;

    public static final String USUARIO_RESPONSE = """
            {
              "id": 1,
              "nome": "Maria Oliveira",
              "email": "maria@restaurante.com",
              "login": "maria.oliveira",
              "dataUltimaAlteracao": "2026-04-29T20:30:00",
              "endereco": {
                "rua": "Rua das Flores",
                "numero": "120",
                "complemento": "Sala 2",
                "cidade": "Sao Paulo",
                "cep": "01001-000"
              },
              "tipoUsuario": "DONO_RESTAURANTE"
            }
            """;

    public static final String USUARIO_LISTA_RESPONSE = """
            [
              {
                "id": 1,
                "nome": "Maria Oliveira",
                "email": "maria@restaurante.com",
                "login": "maria.oliveira",
                "dataUltimaAlteracao": "2026-04-29T20:30:00",
                "endereco": {
                  "rua": "Rua das Flores",
                  "numero": "120",
                  "complemento": "Sala 2",
                  "cidade": "Sao Paulo",
                  "cep": "01001-000"
                },
                "tipoUsuario": "DONO_RESTAURANTE"
              }
            ]
            """;

    public static final String USUARIO_LOGIN_REQUEST = """
            {
              "login": "maria.oliveira",
              "senha": "SenhaSegura123"
            }
            """;

    public static final String USUARIO_LOGIN_RESPONSE = """
            {
              "id": 1,
              "nome": "Maria Oliveira",
              "email": "maria@restaurante.com",
              "login": "maria.oliveira",
              "tipoUsuario": "DONO_RESTAURANTE"
            }
            """;

    public static final String USUARIO_ATUALIZACAO_REQUEST = """
            {
              "nome": "Maria Oliveira Atualizada",
              "email": "maria.atualizada@restaurante.com",
              "login": "maria.atualizada",
              "tipoUsuario": "DONO_RESTAURANTE",
              "endereco": {
                "rua": "Avenida Central",
                "numero": "500",
                "complemento": "Conjunto 10",
                "cidade": "Sao Paulo",
                "cep": "04567-000"
              }
            }
            """;

    public static final String USUARIO_SENHA_REQUEST = """
            {
              "novaSenha": "NovaSenha456"
            }
            """;

    public static final String PROBLEM_VALIDACAO = """
            {
              "type": "about:blank",
              "title": "Dados invalidos",
              "status": 400,
              "detail": "A requisicao possui campos invalidos.",
              "instance": "/api/v1/usuarios",
              "errors": {
                "email": "E-mail invalido"
              }
            }
            """;

    public static final String PROBLEM_CONFLITO = """
            {
              "type": "about:blank",
              "title": "Violacao de regra de negocio",
              "status": 409,
              "detail": "Ja existe um usuario cadastrado com este e-mail.",
              "instance": "/api/v1/usuarios"
            }
            """;

    public static final String PROBLEM_CREDENCIAIS = """
            {
              "type": "about:blank",
              "title": "Credenciais invalidas",
              "status": 401,
              "detail": "Login ou senha invalidos.",
              "instance": "/api/v1/usuarios/login"
            }
            """;

    public static final String PROBLEM_NAO_ENCONTRADO = """
            {
              "type": "about:blank",
              "title": "Recurso nao encontrado",
              "status": 404,
              "detail": "Usuario com id 99 nao foi encontrado.",
              "instance": "/api/v1/usuarios/99"
            }
            """;

    private OpenApiExamples() {
    }
}
