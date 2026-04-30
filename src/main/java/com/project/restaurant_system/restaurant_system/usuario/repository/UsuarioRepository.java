package com.project.restaurant_system.restaurant_system.usuario.repository;

import com.project.restaurant_system.restaurant_system.model.TipoUsuario;
import com.project.restaurant_system.restaurant_system.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    boolean existsByLoginIgnoreCase(String login);

    boolean existsByLoginIgnoreCaseAndIdNot(String login, Long id);

    List<Usuario> findAllByTipoUsuario(TipoUsuario tipoUsuario);

    List<Usuario> findAllByNomeContainingIgnoreCase(String nome);

    List<Usuario> findAllByTipoUsuarioAndNomeContainingIgnoreCase(TipoUsuario tipoUsuario, String nome);

    Optional<Usuario> findByLoginIgnoreCase(String login);
}
