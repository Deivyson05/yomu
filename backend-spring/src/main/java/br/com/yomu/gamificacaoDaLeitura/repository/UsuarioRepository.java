package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
    
    Optional<Usuario> findByCodigoConvite(String codigoConvite);
    
    boolean existsByEmail(String email);
    
    boolean existsByNomeUsuario(String nomeUsuario);
}