package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Amizade;
import br.com.yomu.gamificacaoDaLeitura.model.enums.StatusAmizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmizadeRepository extends JpaRepository<Amizade, UUID> {
    
    @Query("SELECT a FROM Amizade a WHERE " +
           "(a.usuarioId1.id = :usuarioId OR a.usuarioId2.id = :usuarioId) " +
           "AND a.status = :status")
    List<Amizade> findAmizadesByUsuarioAndStatus(
        @Param("usuarioId") UUID usuarioId,
        @Param("status") StatusAmizade status
    );
    
    @Query("SELECT a FROM Amizade a WHERE " +
           "a.usuarioId2.id = :usuarioId AND a.status = 'PENDENTE'")
    List<Amizade> findSolicitacoesPendentes(@Param("usuarioId") UUID usuarioId);
    
    @Query("SELECT a FROM Amizade a WHERE " +
           "((a.usuarioId1.id = :usuario1 AND a.usuarioId2.id = :usuario2) OR " +
           "(a.usuarioId1.id = :usuario2 AND a.usuarioId2.id = :usuario1))")
    Optional<Amizade> findAmizadeEntreUsuarios(
        @Param("usuario1") UUID usuario1,
        @Param("usuario2") UUID usuario2
    );
}
