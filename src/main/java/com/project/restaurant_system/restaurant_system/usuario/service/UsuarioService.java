package com.project.restaurant_system.restaurant_system.usuario.service;

import com.project.restaurant_system.restaurant_system.common.exception.CredenciaisInvalidasException;
import com.project.restaurant_system.restaurant_system.common.exception.RecursoNaoEncontradoException;
import com.project.restaurant_system.restaurant_system.common.exception.RegraDeNegocioException;
import com.project.restaurant_system.restaurant_system.model.Endereco;
import com.project.restaurant_system.restaurant_system.model.TipoUsuario;
import com.project.restaurant_system.restaurant_system.model.Usuario;
import com.project.restaurant_system.restaurant_system.usuario.dto.EnderecoRequest;
import com.project.restaurant_system.restaurant_system.usuario.dto.EnderecoResponse;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioAtualizacaoRequest;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioCriacaoRequest;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioLoginRequest;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioLoginResponse;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioResponse;
import com.project.restaurant_system.restaurant_system.usuario.dto.UsuarioSenhaAtualizacaoRequest;
import com.project.restaurant_system.restaurant_system.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse criar(UsuarioCriacaoRequest request) {
        validarUnicidade(request.email(), request.login(), null);

        Usuario usuario = new Usuario(
                limpar(request.nome()),
                normalizarEmail(request.email()),
                limpar(request.login()),
                passwordEncoder.encode(request.senha()),
                toEndereco(request.endereco()),
                request.tipoUsuario()
        );

        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar(TipoUsuario tipoUsuario, String nome) {
        List<Usuario> usuarios = buscarUsuarios(tipoUsuario, nome);

        return usuarios.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public UsuarioLoginResponse autenticar(UsuarioLoginRequest request) {
        Usuario usuario = usuarioRepository.findByLoginIgnoreCase(limpar(request.login()))
                .orElseThrow(() -> new CredenciaisInvalidasException("Login ou senha invalidos."));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException("Login ou senha invalidos.");
        }

        return new UsuarioLoginResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario()
        );
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioAtualizacaoRequest request) {
        Usuario usuario = buscarEntidadePorId(id);
        validarUnicidade(request.email(), request.login(), id);

        usuario.atualizarDados(
                limpar(request.nome()),
                normalizarEmail(request.email()),
                limpar(request.login()),
                toEndereco(request.endereco()),
                request.tipoUsuario()
        );

        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void atualizarSenha(Long id, UsuarioSenhaAtualizacaoRequest request) {
        Usuario usuario = buscarEntidadePorId(id);
        usuario.atualizarSenha(passwordEncoder.encode(request.novaSenha()));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario com id " + id + " nao foi encontrado."));
    }

    private List<Usuario> buscarUsuarios(TipoUsuario tipoUsuario, String nome) {
        String nomeLimpo = limparOpcional(nome);

        if (tipoUsuario == null && nomeLimpo == null) {
            return usuarioRepository.findAll();
        }

        if (tipoUsuario == null) {
            return usuarioRepository.findAllByNomeContainingIgnoreCase(nomeLimpo);
        }

        if (nomeLimpo == null) {
            return usuarioRepository.findAllByTipoUsuario(tipoUsuario);
        }

        return usuarioRepository.findAllByTipoUsuarioAndNomeContainingIgnoreCase(tipoUsuario, nomeLimpo);
    }

    private void validarUnicidade(String email, String login, Long id) {
        String emailNormalizado = normalizarEmail(email);
        String loginNormalizado = limpar(login);

        boolean emailEmUso = id == null
                ? usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)
                : usuarioRepository.existsByEmailIgnoreCaseAndIdNot(emailNormalizado, id);
        if (emailEmUso) {
            throw new RegraDeNegocioException("Ja existe um usuario cadastrado com este e-mail.");
        }

        boolean loginEmUso = id == null
                ? usuarioRepository.existsByLoginIgnoreCase(loginNormalizado)
                : usuarioRepository.existsByLoginIgnoreCaseAndIdNot(loginNormalizado, id);
        if (loginEmUso) {
            throw new RegraDeNegocioException("Ja existe um usuario cadastrado com este login.");
        }
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getDataUltimaAlteracao(),
                new EnderecoResponse(
                        usuario.getEndereco().getRua(),
                        usuario.getEndereco().getNumero(),
                        usuario.getEndereco().getComplemento(),
                        usuario.getEndereco().getCidade(),
                        usuario.getEndereco().getCep()
                ),
                usuario.getTipoUsuario()
        );
    }

    private Endereco toEndereco(EnderecoRequest request) {
        return new Endereco(
                limpar(request.rua()),
                limpar(request.numero()),
                limparOpcional(request.complemento()),
                limpar(request.cidade()),
                limpar(request.cep())
        );
    }

    private String normalizarEmail(String email) {
        return limpar(email).toLowerCase();
    }

    private String limpar(String valor) {
        return valor == null ? null : valor.trim();
    }

    private String limparOpcional(String valor) {
        String valorLimpo = limpar(valor);
        return StringUtils.hasText(valorLimpo) ? valorLimpo : null;
    }
}
