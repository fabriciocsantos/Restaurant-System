package com.project.restaurant_system.restaurant_system.usuario;

import com.project.restaurant_system.restaurant_system.model.Endereco;
import com.project.restaurant_system.restaurant_system.model.TipoUsuario;
import com.project.restaurant_system.restaurant_system.model.Usuario;
import com.project.restaurant_system.restaurant_system.support.PostgreSQLIntegrationTest;
import com.project.restaurant_system.restaurant_system.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest extends PostgreSQLIntegrationTest {

    private static final String BASE_URL = "/api/v1/usuarios";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void deveCriarUsuarioComSucesso() throws Exception {
        String payload = """
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

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Maria Oliveira"))
                .andExpect(jsonPath("$.email").value("maria@restaurante.com"))
                .andExpect(jsonPath("$.tipoUsuario").value("DONO_RESTAURANTE"))
                .andExpect(jsonPath("$.dataUltimaAlteracao").isNotEmpty())
                .andExpect(jsonPath("$.endereco.cidade").value("Sao Paulo"));
    }

    @Test
    void deveRetornarErroQuandoEmailForDuplicado() throws Exception {
        String payload = """
                {
                  "nome": "Joao Silva",
                  "email": "joao@cliente.com",
                  "login": "joao.silva",
                  "senha": "SenhaSegura123",
                  "tipoUsuario": "CLIENTE",
                  "endereco": {
                    "rua": "Rua A",
                    "numero": "10",
                    "cidade": "Campinas",
                    "cep": "13000-000"
                  }
                }
                """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Violacao de regra de negocio"));
    }

    @Test
    void deveFiltrarUsuariosPorTipo() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("tipoUsuario", "CLIENTE"))
                .andExpect(status().isOk());
    }

    @Test
    void deveBuscarUsuariosPorNome() throws Exception {
        salvarUsuario(
                "Ana Costa",
                "ana.costa@cliente.com",
                "ana.costa",
                "SenhaAtual123",
                TipoUsuario.CLIENTE
        );
        salvarUsuario(
                "Bruno Lima",
                "bruno.lima@cliente.com",
                "bruno.lima",
                "SenhaAtual123",
                TipoUsuario.CLIENTE
        );

        mockMvc.perform(get(BASE_URL)
                        .param("nome", "ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Ana Costa"));
    }

    @Test
    void deveAutenticarUsuarioComLoginESenhaValidos() throws Exception {
        salvarUsuario(
                "Carlos Souza",
                "carlos.souza@cliente.com",
                "carlos.souza",
                "SenhaAtual123",
                TipoUsuario.CLIENTE
        );

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "login": "carlos.souza",
                                  "senha": "SenhaAtual123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Souza"))
                .andExpect(jsonPath("$.login").value("carlos.souza"))
                .andExpect(jsonPath("$.tipoUsuario").value("CLIENTE"));
    }

    @Test
    void deveRetornarErroQuandoCredenciaisForemInvalidas() throws Exception {
        salvarUsuario(
                "Marina Alves",
                "marina.alves@cliente.com",
                "marina.alves",
                "SenhaAtual123",
                TipoUsuario.CLIENTE
        );

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "login": "marina.alves",
                                  "senha": "SenhaErrada999"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value("Credenciais invalidas"));
    }

    @Test
    void deveTrocarSenhaEmEndpointSeparado() throws Exception {
        Usuario usuario = salvarUsuario(
                "Ana Paula Costa",
                "ana.paula@cliente.com",
                "ana.paula.costa",
                "SenhaAtual123",
                TipoUsuario.CLIENTE
        );

        mockMvc.perform(patch(BASE_URL + "/{id}/senha", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "novaSenha": "NovaSenha456"
                                }
                                """))
                .andExpect(status().isNoContent());

        Usuario usuarioAtualizado = usuarioRepository.findById(usuario.getId()).orElseThrow();
        assertThat(passwordEncoder.matches("NovaSenha456", usuarioAtualizado.getSenha())).isTrue();
        assertThat(passwordEncoder.matches("SenhaAtual123", usuarioAtualizado.getSenha())).isFalse();
    }

    @Test
    void deveValidarNovaSenhaNoEndpointSeparado() throws Exception {
        Usuario usuario = salvarUsuario(
                "Paula Lima",
                "paula@cliente.com",
                "paula.lima",
                "SenhaAtual123",
                TipoUsuario.CLIENTE
        );

        mockMvc.perform(patch(BASE_URL + "/{id}/senha", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "novaSenha": "123"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.novaSenha").value("Nova senha deve ter entre 8 e 100 caracteres"));
    }

    private Usuario salvarUsuario(String nome, String email, String login, String senha, TipoUsuario tipoUsuario) {
        return usuarioRepository.save(new Usuario(
                nome,
                email,
                login,
                passwordEncoder.encode(senha),
                new Endereco("Rua Teste", "10", null, "Sao Paulo", "01001-000"),
                tipoUsuario
        ));
    }
}
