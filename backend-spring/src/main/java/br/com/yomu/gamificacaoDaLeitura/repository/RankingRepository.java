package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Ranking;
import br.com.yomu.gamificacaoDaLeitura.model.enums.PeriodoRanking;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, UUID> {

    // Busca ranking específico por tipo e período
    Optional<Ranking> findByTipoRankingAndPeriodoRanking(TipoRanking tipoRanking, PeriodoRanking periodoRanking);

    // Busca todos os rankings de um tipo específico
    List<Ranking> findByTipoRanking(TipoRanking tipoRanking);

    // Busca todos os rankings de um período específico
    List<Ranking> findByPeriodoRanking(PeriodoRanking periodoRanking);

    // Verifica se existe um ranking com tipo e período específicos
    boolean existsByTipoRankingAndPeriodoRanking(TipoRanking tipoRanking, PeriodoRanking periodoRanking);

    // Deleta ranking por tipo e período
    void deleteByTipoRankingAndPeriodoRanking(TipoRanking tipoRanking, PeriodoRanking periodoRanking);
}