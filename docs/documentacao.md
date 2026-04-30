# Documentação do Projeto

## Objetivo

Backend do Tech Challenge para um sistema compartilhado de gestão de restaurantes.

O sistema, nesta etapa, atende dois perfis de usuário:

- Dono de restaurante
- Cliente

Esta documentação é o registro vivo do projeto e deve ser atualizada sempre que houver alteração relevante no código, na arquitetura ou nas regras implementadas.

## Situação Atual do Projeto

### Parte 1 - Usuários

A primeira parte está implementada e validada.

Escopo já coberto:

- Modelagem da entidade `Usuario`
- Separação de perfis com `TipoUsuario`
- Endereço modelado como objeto embutido com JPA
- Validação de dados de entrada com Bean Validation
- Persistência com Spring Data JPA
- CRUD REST de usuários
- Busca de usuários por nome
- Validação simples de login e senha consultando o banco
- Troca de senha em endpoint separado
- Tratamento global de erros com `ProblemDetail`
- Armazenamento de senha com hash BCrypt
- Atualização automática do campo `dataUltimaAlteracao`
- Configuração principal do projeto em PostgreSQL
- Inicialização do schema principal via script SQL na subida da aplicação
- Dockerização da aplicação com Docker Compose e PostgreSQL
- Versionamento de API via prefixo de caminho `/api/v1`
- Documentação Swagger/OpenAPI com exemplos de sucesso e erro
- Coleção Postman em JSON cobrindo os principais cenários de usuários

## Requisitos Atendidos

Com base no enunciado enviado até agora, o projeto atende os seguintes pontos:

- Backend em Spring Boot
- Dois tipos obrigatórios de usuário:
  - `CLIENTE`
  - `DONO_RESTAURANTE`
- Campos obrigatórios de usuário:
  - nome
  - e-mail
  - login
  - senha
  - data da última alteração
  - endereço
- Objetivos do sistema já atendidos nesta etapa:
  - cadastro de usuário
  - atualização de usuário
  - exclusão de usuário
  - busca de usuários por nome
  - validação obrigatória de login e senha
  - troca de senha do usuário em endpoint separado
  - garantia de e-mail único
- Requisitos gerais já atendidos nesta etapa:
  - aplicação dockerizada
  - orquestração com Docker Compose
  - banco relacional PostgreSQL integrado à orquestração
  - estratégia de versionamento de API
  - padronização de erros com `ProblemDetail` (RFC 7807)
  - endpoints documentados com Swagger/OpenAPI
  - exemplos de requisições e respostas de sucesso e erro
  - coleção Postman em formato JSON
- Organização em camadas, com separação entre:
  - controller
  - service
  - repository
  - model
  - dto
  - common/exception

## Decisões Técnicas Adotadas

- `email` foi definido como único por exigência do enunciado.
- `login` também foi definido como único por consistência operacional e preparação para autenticação futura.
- `dataUltimaAlteracao` não é recebida da API; ela é controlada automaticamente pela entidade.
- O endereço foi modelado como objeto, e não como `String`, para permitir evolução mais limpa do domínio.
- A senha já é persistida com hash BCrypt para evitar dívida técnica desde a base do projeto.
- A validação de login foi implementada sem Spring Security, apenas com consulta ao banco e comparação do hash BCrypt.
- A orquestração local foi definida com `docker-compose.yml`, separando a aplicação e o PostgreSQL em serviços distintos.
- A estratégia de versionamento adotada foi via path, usando o prefixo `/api/v1`.
- A documentação Swagger foi enriquecida com exemplos explícitos de request, response de sucesso e erros padronizados.

## Estrutura Atual

Principais arquivos e responsabilidades desta etapa:

- `src/main/java/com/project/restaurant_system/restaurant_system/model/Usuario.java`
  - entidade principal de usuário
- `src/main/java/com/project/restaurant_system/restaurant_system/model/Endereco.java`
  - objeto embutido do endereço
- `src/main/java/com/project/restaurant_system/restaurant_system/model/TipoUsuario.java`
  - enum com os perfis do sistema
- `src/main/java/com/project/restaurant_system/restaurant_system/usuario/controller/UsuarioController.java`
  - endpoints REST de usuários
- `src/main/java/com/project/restaurant_system/restaurant_system/usuario/service/UsuarioService.java`
  - regras de negócio, orquestração do módulo e validação simples de login
- `src/main/java/com/project/restaurant_system/restaurant_system/usuario/dto/UsuarioLoginRequest.java`
  - contrato de entrada do endpoint de login
- `src/main/java/com/project/restaurant_system/restaurant_system/usuario/dto/UsuarioLoginResponse.java`
  - resposta da validação de login
- `src/main/java/com/project/restaurant_system/restaurant_system/usuario/dto/UsuarioSenhaAtualizacaoRequest.java`
  - contrato de entrada do endpoint de troca de senha
- `src/main/java/com/project/restaurant_system/restaurant_system/usuario/repository/UsuarioRepository.java`
  - acesso aos dados e filtros de busca
- `src/main/java/com/project/restaurant_system/restaurant_system/common/exception/GlobalExceptionHandler.java`
  - tratamento padronizado de exceções
- `src/main/java/com/project/restaurant_system/restaurant_system/common/config/OpenApiConfig.java`
  - metadados principais da documentação OpenAPI
- `src/main/java/com/project/restaurant_system/restaurant_system/common/config/OpenApiExamples.java`
  - exemplos reutilizados nas anotações Swagger/OpenAPI
- `docs/restaurant-system.postman_collection.json`
  - coleção Postman com cenários principais do módulo de usuários
- `docs/relatorio-tecnico.html`
  - fonte do relatório técnico consolidado da entrega
- `docs/relatorio-tecnico.pdf`
  - PDF final do relatório técnico
- `docs/assets/swagger-ui.png`
  - captura visual real do Swagger UI utilizada no relatório técnico
- `docs/assets/postman-collection.png`
  - visualização consolidada da coleção Postman utilizada no relatório técnico
- `docs/assets/postman-preview.html`
  - fonte HTML da visualização usada para gerar a figura da coleção Postman no relatório técnico
- `README.md`
  - visão geral do projeto, formas de execução, Swagger, Postman e artefatos principais
- `Dockerfile`
  - build e empacotamento da aplicação em imagem Docker
- `docker-compose.yml`
  - orquestração da aplicação com PostgreSQL

## Endpoints Disponíveis

Base da API:

- `POST /api/v1/usuarios`
- `POST /api/v1/usuarios/login`
- `GET /api/v1/usuarios`
- `GET /api/v1/usuarios/{id}`
- `PUT /api/v1/usuarios/{id}`
- `PATCH /api/v1/usuarios/{id}/senha`
- `DELETE /api/v1/usuarios/{id}`

Filtro disponível:

- `GET /api/v1/usuarios?tipoUsuario=CLIENTE`
- `GET /api/v1/usuarios?tipoUsuario=DONO_RESTAURANTE`
- `GET /api/v1/usuarios?nome=ana`
- `GET /api/v1/usuarios?tipoUsuario=CLIENTE&nome=ana`

Validação simples de login:

- `POST /api/v1/usuarios/login`

## Modelo Atual de Usuário

Campos persistidos atualmente:

- `id`
- `nome`
- `email`
- `login`
- `senha`
- `dataUltimaAlteracao`
- `tipoUsuario`
- `rua`
- `numero`
- `complemento`
- `cidade`
- `cep`

## Banco de Dados no Estado Atual

No estado atual, o projeto já possui persistência funcional para o módulo de usuários.

Configuração observada no projeto:

- Banco principal: PostgreSQL
- Banco de testes: PostgreSQL via Testcontainers
- Driver de PostgreSQL configurado no projeto
- `src/main/resources/schema.sql` como fonte de criação da estrutura principal
- `spring.jpa.hibernate.ddl-auto=none` no ambiente principal
- `spring.sql.init.mode=always` no ambiente principal
- `spring.jpa.hibernate.ddl-auto=create-drop` nos testes
- `spring.sql.init.mode=never` nos testes

Variáveis de ambiente suportadas:

- `DATABASE_URL`
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`

Observação:

- Até aqui, a modelagem de banco cobre apenas a estrutura de usuários.
- A modelagem completa para restaurantes, cardápio, pedidos e avaliações ainda não foi implementada.
- O banco exposto pelo Docker Compose usa a porta `5433` no host para não conflitar com o PostgreSQL local já utilizado no desenvolvimento.

### Configuração padrão atual

O projeto está configurado para subir, por padrão, usando:

- URL: `jdbc:postgresql://localhost:5432/restaurant_system`
- usuário: `postgres`
- senha: `postgres`

Esses valores podem ser substituídos por variáveis de ambiente.

### Inicialização automática do banco ao subir o projeto

Ao iniciar a aplicação, o Spring executa automaticamente o arquivo:

- `src/main/resources/schema.sql`

Esse script cria a tabela `usuarios` com base no modelo atual da aplicação.

Pré-requisito:

- existir um servidor PostgreSQL local acessível em `localhost:5432`
- existir o banco `restaurant_system`
- existir um usuário compatível com a configuração do projeto

### Uso com DBeaver

O DBeaver é o cliente de banco. O backend não se conecta ao DBeaver; ele se conecta ao servidor PostgreSQL.

Se você usar a configuração padrão do projeto, a conexão no DBeaver deve ficar assim:

- Host: `localhost`
- Porta: `5432`
- Database: `restaurant_system`
- Username: `postgres`
- Password: `postgres`

### Execução com Docker Compose

Arquivos disponíveis no projeto:

- `Dockerfile`
- `docker-compose.yml`
- `.dockerignore`

Para subir a aplicação e o banco via Docker Compose:

```bash
docker compose up --build
```

Após a subida:

- aplicação: `http://localhost:8080`
- PostgreSQL exposto no host: `localhost:5433`

Se quiser acessar esse banco containerizado pelo DBeaver, use:

- Host: `localhost`
- Porta: `5433`
- Database: `restaurant_system`
- Username: `postgres`
- Password: `postgres`

### Swagger / OpenAPI

Com a aplicação em execução, a documentação pode ser acessada em:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

Os endpoints do módulo de usuários estão documentados com:

- descrição da operação
- exemplos de requisição
- exemplos de resposta de sucesso
- exemplos de resposta de erro com `ProblemDetail`

### Postman

Coleção disponível no projeto:

- `docs/restaurant-system.postman_collection.json`

Cenários cobertos na coleção:

- cadastro de usuário válido
- tentativa de cadastro inválido por e-mail duplicado
- tentativa de cadastro inválido por campos obrigatórios faltando
- validação de login com sucesso
- validação de login com erro
- atualização de dados com sucesso
- atualização de dados com erro
- busca de usuários por nome
- alteração de senha com sucesso
- alteração de senha com erro

### Relatório Técnico

Arquivos disponíveis no projeto:

- `docs/relatorio-tecnico.html`
- `docs/relatorio-tecnico.pdf`

Conteúdo consolidado no relatório:

- descrição detalhada da arquitetura da aplicação
- modelagem das entidades e relacionamentos
- descrição dos endpoints disponíveis com exemplos
- descrição da documentação Swagger/OpenAPI
- descrição da coleção Postman
- estrutura atual do banco de dados
- passo a passo de execução com Docker Compose, variáveis de ambiente e portas
- figuras e evidências visuais do Swagger e da coleção Postman

Observações desta versão do relatório:

- o PDF foi reestruturado para ser autossuficiente como entregável único
- a exportação final foi gerada a partir do HTML com impressão em PDF via Chrome headless
- foram incorporadas capturas e visualizações para fortalecer as seções de Swagger e Postman
- o PDF final foi regenerado sem cabeçalho e rodapé automáticos para remover o caminho local do arquivo HTML
- a seção `9.4 Passo a passo de execução` foi expandida para um roteiro completo de uso com Docker Compose, incluindo pré-requisitos, build, subida, verificação, acesso ao banco e encerramento

### README do Repositório

O `README.md` do projeto foi consolidado para servir como ponto de entrada do repositório, contendo:

- visão geral do backend
- stack principal
- funcionalidades implementadas
- endpoints disponíveis
- instruções de execução local
- instruções de execução com Docker Compose
- acesso à documentação Swagger/OpenAPI
- localização da coleção Postman
- localização do relatório técnico

Observação:

- o repositório remoto configurado é `https://github.com/fabriciocsantos/Restaurant-System`
- para que o GitHub reflita integralmente o estado atual do projeto, os arquivos locais precisam estar commitados e enviados ao remoto

## Stack Atual

Dependências principais identificadas no projeto:

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Validation
- Spring Web MVC
- SpringDoc OpenAPI
- BCrypt via `spring-security-crypto`
- PostgreSQL
- Docker
- Docker Compose
- Testcontainers PostgreSQL para testes
- Lombok

## Qualidade e Padrão do Código

Pontos aplicados nesta etapa:

- Separação clara de responsabilidades
- DTOs para entrada e saída
- Regra de negócio fora do controller
- Resposta de API sem exposição direta da senha
- Validação centralizada
- Tratamento consistente de erros
- Cobertura inicial com testes automatizados

## Testes Atuais

Arquivos de teste existentes:

- `src/test/java/com/project/restaurant_system/restaurant_system/RestaurantSystemApplicationTests.java`
- `src/test/java/com/project/restaurant_system/restaurant_system/usuario/UsuarioControllerTest.java`

Cobertura atual dos testes:

- subida do contexto Spring
- criação de usuário
- validação de duplicidade de e-mail
- listagem com filtro por tipo de usuário
- busca de usuários por nome
- autenticação simples com login e senha válidos
- retorno de erro para credenciais inválidas
- troca de senha em endpoint separado
- validação da nova senha no endpoint separado

## Verificação Realizada

Última verificação executada em `29/04/2026`:

- comando executado: `./mvnw -DskipTests spring-boot:run`
- resultado: aplicação iniciada com sucesso em `http://localhost:8080` usando PostgreSQL local e inicialização por `schema.sql`
- comando executado: `./mvnw test`
- resultado: `BUILD SUCCESS`
- total validado: 9 testes executados, 0 falhas e 0 erros
- observação: os testes de integração usam PostgreSQL em container Docker via Testcontainers
- comando executado: `docker compose config`
- resultado: configuração do Compose validada com sucesso
- comando executado: `docker compose build`
- resultado: imagens da aplicação e da orquestração geradas com sucesso
- comando executado: `docker compose up -d`
- resultado: aplicação e PostgreSQL iniciados com sucesso via Compose
- comando executado: `GET http://localhost:8080/api/v1/usuarios`
- resultado: resposta `200 OK` com a stack em containers
- comando executado: `./mvnw test`
- resultado: documentação Swagger/OpenAPI compilada com sucesso junto da suíte de testes
- comando executado: `GET http://localhost:8080/v3/api-docs`
- resultado: documentação OpenAPI exposta com exemplos e rotas versionadas
- comando executado: validação sintática da coleção Postman em JSON
- resultado: arquivo da coleção válido
- comando executado: geração do relatório técnico em PDF a partir de `docs/relatorio-tecnico.html`
- resultado: PDF final consolidado em `docs/relatorio-tecnico.pdf`, com 18 páginas, imagens incorporadas e sem caminho local no rodapé

## Histórico de Atualizações

### 29/04/2026

- Criada a base do módulo de usuários.
- Adicionado CRUD REST para usuários.
- Adicionada busca de usuários por nome.
- Adicionada validação simples de login e senha sem Spring Security.
- Adicionado endpoint separado para troca de senha de usuário.
- Adicionados `Dockerfile`, `.dockerignore` e `docker-compose.yml` para containerização da aplicação com PostgreSQL.
- Adicionado versionamento de API via prefixo `/api/v1`.
- Adicionadas anotações Swagger/OpenAPI com exemplos de sucesso e erro.
- Adicionada coleção Postman em JSON com os cenários principais do módulo de usuários.
- Adicionado relatório técnico consolidado em HTML e PDF.
- Adicionadas evidências visuais do Swagger UI e da coleção Postman ao relatório técnico.
- Regenerado o PDF final sem cabeçalho e rodapé automáticos para evitar exposição do caminho local do arquivo.
- Expandida a seção de execução com Docker no relatório técnico para um passo a passo operacional mais completo.
- Adicionado tratamento global de erros.
- Adicionada criptografia de senha com BCrypt.
- Removido H2 do projeto.
- Configurado PostgreSQL como banco principal.
- Alterada a criação do schema principal para `schema.sql` executado na subida da aplicação.
- Configurados testes para PostgreSQL com Testcontainers.
- Desabilitada a inicialização SQL automática nos testes para evitar conflito com `create-drop`.
- Adicionado `schema.sql` para criar a estrutura principal ao subir a aplicação.
- Documentado o uso do banco com DBeaver.
- Criada e revisada a documentação viva do projeto em português do Brasil.
