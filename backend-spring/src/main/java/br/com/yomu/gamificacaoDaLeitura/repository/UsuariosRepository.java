package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByNomeUsuario(String nomeUsuario);

    boolean existsByEmail(String email);

    Optional<Usuario> findByNomeUsuario(String nomeUsuario);

    Optional<Usuario> findByCodigoConvite(String codigoConvite);
}
