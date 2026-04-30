# Restaurant System

Backend do Tech Challenge para um sistema compartilhado de gestão de restaurantes.

O escopo implementado nesta etapa está concentrado no módulo de usuários, com API REST,
persistência em PostgreSQL, documentação Swagger/OpenAPI, coleção Postman e execução com Docker Compose.

## Stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Docker e Docker Compose
- SpringDoc OpenAPI
- Testcontainers

## Funcionalidades implementadas

- cadastro de usuário
- atualização de dados cadastrais
- exclusão de usuário
- troca de senha em endpoint separado
- validação simples de login e senha
- busca de usuários por nome
- filtro por tipo de usuário
- tratamento padronizado de erros com `ProblemDetail`
- versionamento de API via `/api/v1`

## Tipos de usuário

O projeto contempla os dois tipos obrigatórios do desafio:

- `CLIENTE`
- `DONO_RESTAURANTE`

## Endpoints disponíveis

Base da API:

- `POST /api/v1/usuarios`
- `POST /api/v1/usuarios/login`
- `GET /api/v1/usuarios`
- `GET /api/v1/usuarios/{id}`
- `PUT /api/v1/usuarios/{id}`
- `PATCH /api/v1/usuarios/{id}/senha`
- `DELETE /api/v1/usuarios/{id}`

Exemplos de filtro:

- `GET /api/v1/usuarios?nome=Maria`
- `GET /api/v1/usuarios?tipoUsuario=CLIENTE`
- `GET /api/v1/usuarios?tipoUsuario=DONO_RESTAURANTE&nome=Maria`

## Execução local

Pré-requisitos:

- Java 21
- Maven Wrapper
- PostgreSQL disponível em `localhost:5432`
- banco `restaurant_system`

Configuração padrão:

- URL: `jdbc:postgresql://localhost:5432/restaurant_system`
- usuário: `postgres`
- senha: `postgres`

Subida da aplicação:

```bash
./mvnw spring-boot:run
```

## Execução com Docker Compose

O projeto inclui:

- `Dockerfile`
- `docker-compose.yml`

Para subir aplicação e banco:

```bash
docker compose up --build
```

Serviços expostos:

- aplicação: `http://localhost:8080`
- PostgreSQL no host: `localhost:5433`

Credenciais do banco containerizado:

- database: `restaurant_system`
- username: `postgres`
- password: `postgres`

Para encerrar:

```bash
docker compose down
```

## Swagger / OpenAPI

Com a aplicação em execução:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Coleção Postman

Arquivo disponível no projeto:

- [docs/restaurant-system.postman_collection.json](docs/restaurant-system.postman_collection.json)

Principais cenários cobertos:

- cadastro válido
- cadastro inválido por duplicidade
- cadastro inválido por validação
- login com sucesso e erro
- atualização de dados com sucesso e erro
- troca de senha com sucesso e erro
- busca por nome

## Relatório técnico

Arquivos disponíveis:

- [docs/relatorio-tecnico.pdf](docs/relatorio-tecnico.pdf)
- [docs/relatorio-tecnico.html](docs/relatorio-tecnico.html)

## Documentação complementar

- [docs/projeto.md](docs/documentacao.md)

## Observação sobre o repositório remoto

O repositório remoto configurado é:

- `https://github.com/fabriciocsantos/Restaurant-System`

Para que o GitHub contenha todas as entregas desta etapa, os arquivos locais ainda precisam estar commitados e enviados para o remoto.
