package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Meta;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MetaRepository extends JpaRepository<Meta, UUID> {
    
    List<Meta> findByUsuarioId(UUID usuarioId);
    
    List<Meta> findByUsuarioIdAndConcluida(UUID usuarioId, Boolean concluida);
    
    @Query("SELECT m FROM Meta m WHERE m.usuario.id = :usuarioId " +
           "AND m.dataFim >= :agora AND m.concluida = false")
    List<Meta> findMetasAtivasUsuario(
        @Param("usuarioId") UUID usuarioId,
        @Param("agora") LocalDateTime agora
    );
    
    List<Meta> findByUsuarioIdAndTipoMeta(UUID usuarioId, TipoMeta tipoMeta);
}