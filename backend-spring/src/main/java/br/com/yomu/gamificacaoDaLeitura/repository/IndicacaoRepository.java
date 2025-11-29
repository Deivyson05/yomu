package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Indicacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IndicacaoRepository extends JpaRepository<Indicacao, UUID> {

    List<Indicacao> findByUsuarioIndicadoId(UUID usuarioIndicadoId);

    List<Indicacao> findByUsuarioIndicadorId(UUID usuarioIndicadorId);
}
