package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Ranking;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.model.enums.PeriodoRanking;
import br.com.yomu.gamificacaoDaLeitura.model.enums.StatusAmizade;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoRanking;
import br.com.yomu.gamificacaoDaLeitura.repository.AmizadeRepository;
import br.com.yomu.gamificacaoDaLeitura.repository.ProgressoRepository;
import br.com.yomu.gamificacaoDaLeitura.repository.RankingRepository;
import br.com.yomu.gamificacaoDaLeitura.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProgressoRepository progressoRepository;
    private final AmizadeRepository amizadeRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Ranking calcularERankingGeral(PeriodoRanking periodo) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        Map<String, Object> dadosRanking = calcularRanking(usuarios, periodo, null);
        
        return salvarRanking(TipoRanking.GERAL, periodo, dadosRanking);
    }

    @Transactional
    public Ranking calcularERankingAmigos(UUID usuarioId, PeriodoRanking periodo) {
        // Buscar amigos do usuário
        List<Usuario> amigos = buscarAmigosUsuario(usuarioId);
        
        // Adicionar o próprio usuário ao ranking
        usuarioRepository.findById(usuarioId).ifPresent(amigos::add);
        
        Map<String, Object> dadosRanking = calcularRanking(amigos, periodo, usuarioId);
        
        return salvarRanking(TipoRanking.AMIGOS, periodo, dadosRanking);
    }

    public Ranking obterRanking(TipoRanking tipo, PeriodoRanking periodo) {
        return rankingRepository.findByTipoRankingAndPeriodoRanking(tipo, periodo)
            .orElseThrow(() -> new IllegalArgumentException("Ranking não encontrado. Execute o cálculo primeiro."));
    }

    public Map<String, Object> obterRankingComoMapa(TipoRanking tipo, PeriodoRanking periodo) {
        Ranking ranking = obterRanking(tipo, periodo);
        try {
            return objectMapper.readValue(ranking.getDados(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar dados do ranking", e);
        }
    }

    @Transactional
    public void atualizarTodosRankings() {
        // Atualizar rankings gerais
        for (PeriodoRanking periodo : PeriodoRanking.values()) {
            calcularERankingGeral(periodo);
        }
    }

    private Map<String, Object> calcularRanking(List<Usuario> usuarios, PeriodoRanking periodo, UUID usuarioContexto) {
        LocalDateTime dataInicio = calcularDataInicio(periodo);
        LocalDateTime dataFim = LocalDateTime.now();
        
        List<Map<String, Object>> ranking = usuarios.stream()
            .map(usuario -> {
                Long xpPeriodo = periodo == PeriodoRanking.TOTAL 
                    ? usuario.getXpTotal()
                    : progressoRepository.findByUsuarioIdAndPeriodo(usuario.getId(), dataInicio, dataFim)
                        .stream()
                        .mapToLong(p -> p.getXpGerado())
                        .sum();
                
                Map<String, Object> item = new HashMap<>();
                item.put("usuario_id", usuario.getId().toString());
                item.put("nome_usuario", usuario.getNomeUsuario());
                item.put("foto_perfil", usuario.getFotoPerfil());
                item.put("nivel", usuario.getNivelAtual());
                item.put("xp", xpPeriodo);
                
                return item;
            })
            .sorted((a, b) -> Long.compare((Long) b.get("xp"), (Long) a.get("xp")))
            .collect(Collectors.toList());
        
        // Adicionar posições
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).put("posicao", i + 1);
        }
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("ranking", ranking);
        resultado.put("total_usuarios", ranking.size());
        resultado.put("periodo", periodo.name());
        resultado.put("data_atualizacao", LocalDateTime.now().toString());
        
        if (usuarioContexto != null) {
            resultado.put("usuario_contexto", usuarioContexto.toString());
        }
        
        return resultado;
    }

    private Ranking salvarRanking(TipoRanking tipo, PeriodoRanking periodo, Map<String, Object> dados) {
        try {
            String dadosJson = objectMapper.writeValueAsString(dados);
            
            Optional<Ranking> rankingExistente = rankingRepository.findByTipoRankingAndPeriodoRanking(tipo, periodo);
            
            Ranking ranking;
            if (rankingExistente.isPresent()) {
                ranking = rankingExistente.get();
                ranking.setDados(dadosJson);
            } else {
                ranking = new Ranking();
                ranking.setTipoRanking(tipo);
                ranking.setPeriodoRanking(periodo);
                ranking.setDados(dadosJson);
            }
            
            return rankingRepository.save(ranking);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao salvar ranking", e);
        }
    }

    private List<Usuario> buscarAmigosUsuario(UUID usuarioId) {
        return amizadeRepository.findAmizadesByUsuarioAndStatus(usuarioId, StatusAmizade.ACEITA)
            .stream()
            .map(amizade -> {
                if (amizade.getUsuarioId1().getId().equals(usuarioId)) {
                    return amizade.getUsuarioId2();
                } else {
                    return amizade.getUsuarioId1();
                }
            })
            .collect(Collectors.toList());
    }

    private LocalDateTime calcularDataInicio(PeriodoRanking periodo) {
        if (periodo.getDias() == null) {
            return LocalDateTime.of(2000, 1, 1, 0, 0); // Data muito antiga para TOTAL
        }
        return LocalDateTime.now().minusDays(periodo.getDias());
    }
}