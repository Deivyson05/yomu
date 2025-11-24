
package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Notificacao;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {
    
    List<Notificacao> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
    
    List<Notificacao> findByUsuarioIdAndLida(UUID usuarioId, Boolean lida);
    
    long countByUsuarioIdAndLida(UUID usuarioId, Boolean lida);
    
    List<Notificacao> findByUsuarioIdAndTipoNotificacao(UUID usuarioId, TipoNotificacao tipo);
}