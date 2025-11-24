package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Progresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProgressoRepository extends JpaRepository<Progresso, UUID> {
    
    List<Progresso> findByLivroId(UUID livroId);
    
    List<Progresso> findByUsuarioId(UUID usuarioId);
    
    List<Progresso> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
    
    @Query("SELECT p FROM Progresso p WHERE p.usuario.id = :usuarioId " +
           "AND p.createdAt BETWEEN :dataInicio AND :dataFim")
    List<Progresso> findByUsuarioIdAndPeriodo(
        @Param("usuarioId") UUID usuarioId,
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("dataFim") LocalDateTime dataFim
    );
    
    @Query("SELECT SUM(p.xpGerado) FROM Progresso p WHERE p.usuario.id = :usuarioId")
    Long calcularXpTotalUsuario(@Param("usuarioId") UUID usuarioId);
}
