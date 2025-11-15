package com.yomu.repository;

import com.yomu.entity.Amizade;
import com.yomu.enums.StatusAmizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmizadeRepository extends JpaRepository<Amizade, Integer> {
    
    /**
     * Busca uma amizade entre dois usuários (independente da ordem)
     */
    @Query("SELECT a FROM Amizade a WHERE " +
           "(a.usuarioId1 = :id1 AND a.usuarioId2 = :id2) OR " +
           "(a.usuarioId1 = :id2 AND a.usuarioId2 = :id1)")
    Optional<Amizade> findByUsuarios(@Param("id1") Integer id1, 
                                      @Param("id2") Integer id2);
    
    /**
     * Lista todas as amizades de um usuário com determinado status
     */
    @Query("SELECT a FROM Amizade a WHERE " +
           "(a.usuarioId1 = :usuarioId OR a.usuarioId2 = :usuarioId) " +
           "AND a.statusAmizade = :status")
    List<Amizade> findByUsuarioAndStatus(@Param("usuarioId") Integer usuarioId,
                                         @Param("status") StatusAmizade status);
    
    /**
     * Lista todas as solicitações de amizade pendentes RECEBIDAS por um usuário
     */
    @Query("SELECT a FROM Amizade a WHERE " +
           "a.usuarioId2 = :usuarioId AND a.statusAmizade = 'PENDENTE'")
    List<Amizade> findSolicitacoesRecebidas(@Param("usuarioId") Integer usuarioId);
    
    /**
     * Lista todas as solicitações de amizade pendentes ENVIADAS por um usuário
     */
    @Query("SELECT a FROM Amizade a WHERE " +
           "a.usuarioId1 = :usuarioId AND a.statusAmizade = 'PENDENTE'")
    List<Amizade> findSolicitacoesEnviadas(@Param("usuarioId") Integer usuarioId);
    
    /**
     * Lista todos os amigos aceitos de um usuário
     */
    @Query("SELECT a FROM Amizade a WHERE " +
           "(a.usuarioId1 = :usuarioId OR a.usuarioId2 = :usuarioId) " +
           "AND a.statusAmizade = 'ACEITA'")
    List<Amizade> findAmigosAceitos(@Param("usuarioId") Integer usuarioId);
    
    /**
     * Lista amigos favoritos de um usuário
     */
    @Query("SELECT a FROM Amizade a WHERE " +
           "(a.usuarioId1 = :usuarioId OR a.usuarioId2 = :usuarioId) " +
           "AND a.statusAmizade = 'ACEITA' AND a.ehFavorito = true")
    List<Amizade> findAmigosFavoritos(@Param("usuarioId") Integer usuarioId);
    
    /**
     * Conta quantos amigos um usuário tem
     */
    @Query("SELECT COUNT(a) FROM Amizade a WHERE " +
           "(a.usuarioId1 = :usuarioId OR a.usuarioId2 = :usuarioId) " +
           "AND a.statusAmizade = 'ACEITA'")
    Long countAmigos(@Param("usuarioId") Integer usuarioId);
    
    /**
     * Verifica se dois usuários são amigos
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Amizade a WHERE " +
           "((a.usuarioId1 = :id1 AND a.usuarioId2 = :id2) OR " +
           "(a.usuarioId1 = :id2 AND a.usuarioId2 = :id1)) " +
           "AND a.statusAmizade = 'ACEITA'")
    boolean saoAmigos(@Param("id1") Integer id1, @Param("id2") Integer id2);
}
