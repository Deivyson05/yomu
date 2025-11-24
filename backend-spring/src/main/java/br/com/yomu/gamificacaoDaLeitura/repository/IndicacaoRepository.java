package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Indicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IndicacaoRepository extends JpaRepository<Indicacao, UUID> {
    
    List<Indicacao> findByDestinatarioId(UUID destinatarioId);
    
    List<Indicacao> findByDestinatarioIdAndLida(UUID destinatarioId, Boolean lida);
    
    List<Indicacao> findByRemetenteId(UUID remetenteId);
    
    List<Indicacao> findByDestinatarioIdOrderByCreatedAtDesc(UUID destinatarioId);
    
    long countByDestinatarioIdAndLida(UUID destinatarioId, Boolean lida);
}