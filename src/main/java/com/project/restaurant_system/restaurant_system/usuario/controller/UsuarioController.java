package com.project.restaurant_system.restaurant_system.usuario.controller;

import com.project.restaurant_system.restaurant_system.common.config.OpenApiExamples;
import com.project.restaurant_system.restaurant_system.model.TipoUsuario;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioAtualizacaoRequest;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioCriacaoRequest;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioLoginRequest;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioLoginResponse;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioResponse;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioSenhaAtualizacaoRequest;
import com.project.restaurant_system.restaurant_system.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Cadastro, autenticacao e gerenciamento de usuarios.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar usuario", description = "Cadastra um novo usuario do tipo CLIENTE ou DONO_RESTAURANTE.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Dados para criacao do usuario.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioCriacaoRequest.class),
                    examples = {
                            @ExampleObject(name = "CriacaoUsuario", value = OpenApiExamples.USUARIO_CRIACAO_REQUEST)
                    }
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponse.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.USUARIO_RESPONSE)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada invalidos.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_VALIDACAO)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito de e-mail ou login duplicado.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_CONFLITO)
                            }
                    )
            )
    })
    public UsuarioResponse criar(@Valid @RequestBody UsuarioCriacaoRequest request) {
        return usuarioService.criar(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario", description = "Valida login e senha do usuario sem uso de Spring Security.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Credenciais do usuario.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioLoginRequest.class),
                    examples = {
                            @ExampleObject(name = "Login", value = OpenApiExamples.USUARIO_LOGIN_REQUEST)
                    }
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Credenciais validadas com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioLoginResponse.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.USUARIO_LOGIN_RESPONSE)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de login invalidos.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_VALIDACAO)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Login ou senha invalidos.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_CREDENCIAIS)
                            }
                    )
            )
    })
    public UsuarioLoginResponse autenticar(@Valid @RequestBody UsuarioLoginRequest request) {
        return usuarioService.autenticar(request);
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Lista usuarios com filtros opcionais por tipo e nome.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuarios retornada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponse.class)),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.USUARIO_LISTA_RESPONSE)
                            }
                    )
            )
    })
    public List<UsuarioResponse> listar(
            @Parameter(description = "Filtro opcional pelo tipo do usuario.", example = "CLIENTE")
            @RequestParam(required = false) TipoUsuario tipoUsuario,
            @Parameter(description = "Filtro opcional por parte do nome do usuario.", example = "ana")
            @RequestParam(required = false) String nome
    ) {
        return usuarioService.listar(tipoUsuario, nome);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por id", description = "Retorna um usuario especifico pelo identificador.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponse.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.USUARIO_RESPONSE)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario nao encontrado.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_NAO_ENCONTRADO)
                            }
                    )
            )
    })
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuario", description = "Atualiza os dados cadastrais do usuario, exceto a senha.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Dados para atualizacao do usuario.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioAtualizacaoRequest.class),
                    examples = {
                            @ExampleObject(name = "AtualizacaoUsuario", value = OpenApiExamples.USUARIO_ATUALIZACAO_REQUEST)
                    }
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponse.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.USUARIO_RESPONSE)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de atualizacao invalidos.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_VALIDACAO)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario nao encontrado.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_NAO_ENCONTRADO)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito de e-mail ou login duplicado.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_CONFLITO)
                            }
                    )
            )
    })
    public UsuarioResponse atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioAtualizacaoRequest request) {
        return usuarioService.atualizar(id, request);
    }

    @PatchMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Trocar senha", description = "Atualiza a senha do usuario em endpoint separado.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Nova senha do usuario.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioSenhaAtualizacaoRequest.class),
                    examples = {
                            @ExampleObject(name = "TrocaSenha", value = OpenApiExamples.USUARIO_SENHA_REQUEST)
                    }
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Nova senha invalida.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_VALIDACAO)
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario nao encontrado.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_NAO_ENCONTRADO)
                            }
                    )
            )
    })
    public void atualizarSenha(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaAtualizacaoRequest request) {
        usuarioService.atualizarSenha(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir usuario", description = "Remove um usuario pelo identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario removido com sucesso."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario nao encontrado.",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = {
                                    @ExampleObject(value = OpenApiExamples.PROBLEM_NAO_ENCONTRADO)
                            }
                    )
            )
    })
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}
