package br.com.yomu.gamificacaoDaLeitura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface de repositório para operações de banco de dados da entidade Usuario.
 * 
 * Herda métodos CRUD básicos do JpaRepository e define consultas personalizadas.
 */
@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, UUID> {
    
    // Busca usuário pelo nome de usuário
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);

    // Busca usuário pelo email
    Optional<Usuario> findByEmail(String email);

    // Busca usuário pelo código de convite
    Optional<Usuario> findByCodigoConvite(String codigoConvite);

    // Verifica se já existe usuário com este nome
    boolean existsByNomeUsuario(String nomeUsuario);

    // Verifica se já existe usuário com este email
    boolean existsByEmail(String email);
}