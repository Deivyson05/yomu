package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Ranking;
import br.com.yomu.gamificacaoDaLeitura.model.enums.PeriodoRanking;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoRanking;
import br.com.yomu.gamificacaoDaLeitura.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/rankings")
@RequiredArgsConstructor
@Tag(name = "Rankings", description = "Sistema de classificação de usuários por XP e leitura")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/geral/{periodo}")
    @Operation(summary = "Obter ranking geral", 
               description = "Retorna o ranking de todos os usuários do sistema para o período especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ranking obtido com sucesso",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "404", description = "Ranking não encontrado. Execute o cálculo primeiro.",
            content = @Content)
    })
    public ResponseEntity<Map<String, Object>> obterRankingGeral(
            @Parameter(description = "Período do ranking", example = "SEMANAL") 
            @PathVariable PeriodoRanking periodo) {
        Map<String, Object> ranking = rankingService.obterRankingComoMapa(TipoRanking.GERAL, periodo);
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/amigos/{usuarioId}/{periodo}")
    @Operation(summary = "Obter ranking de amigos", 
               description = "Retorna o ranking apenas dos amigos de um usuário específico para o período especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ranking de amigos obtido com sucesso",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "404", description = "Ranking não encontrado ou usuário sem amigos",
            content = @Content)
    })
    public ResponseEntity<Map<String, Object>> obterRankingAmigos(
            @Parameter(description = "UUID do usuário") @PathVariable UUID usuarioId,
            @Parameter(description = "Período do ranking", example = "MENSAL") @PathVariable PeriodoRanking periodo) {
        Ranking ranking = rankingService.calcularERankingAmigos(usuarioId, periodo);
        
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> dados = mapper.readValue(ranking.getDados(), Map.class);
            return ResponseEntity.ok(dados);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar ranking", e);
        }
    }

    @PostMapping("/calcular/geral/{periodo}")
    @Operation(summary = "Calcular ranking geral", 
               description = "Recalcula e atualiza o ranking geral para o período especificado. Útil para atualizar o cache.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ranking calculado com sucesso",
            content = @Content(schema = @Schema(implementation = Ranking.class))),
        @ApiResponse(responseCode = "500", description = "Erro ao calcular ranking",
            content = @Content)
    })
    public ResponseEntity<Ranking> calcularRankingGeral(
            @Parameter(description = "Período do ranking", example = "ANUAL") 
            @PathVariable PeriodoRanking periodo) {
        Ranking ranking = rankingService.calcularERankingGeral(periodo);
        return ResponseEntity.ok(ranking);
    }

    @PostMapping("/calcular/amigos/{usuarioId}/{periodo}")
    @Operation(summary = "Calcular ranking de amigos", 
               description = "Recalcula e retorna o ranking dos amigos de um usuário para o período especificado")
    public ResponseEntity<Ranking> calcularRankingAmigos(
            @Parameter(description = "UUID do usuário") @PathVariable UUID usuarioId,
            @Parameter(description = "Período do ranking", example = "TOTAL") @PathVariable PeriodoRanking periodo) {
        Ranking ranking = rankingService.calcularERankingAmigos(usuarioId, periodo);
        return ResponseEntity.ok(ranking);
    }

    @PostMapping("/atualizar-todos")
    @Operation(summary = "Atualizar todos os rankings", 
               description = "Recalcula todos os rankings gerais (semanal, mensal, anual e total). Operação pesada, use com moderação.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todos os rankings foram atualizados com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro ao atualizar rankings")
    })
    public ResponseEntity<String> atualizarTodosRankings() {
        rankingService.atualizarTodosRankings();
        return ResponseEntity.ok("Todos os rankings foram atualizados com sucesso!");
    }

    @GetMapping("/entidade/{tipo}/{periodo}")
    @Operation(summary = "Obter entidade de ranking", 
               description = "Retorna a entidade completa de ranking (incluindo metadados) ao invés do JSON processado")
    public ResponseEntity<Ranking> obterEntidadeRanking(
            @Parameter(description = "Tipo do ranking", example = "GERAL") @PathVariable TipoRanking tipo,
            @Parameter(description = "Período do ranking", example = "SEMANAL") @PathVariable PeriodoRanking periodo) {
        Ranking ranking = rankingService.obterRanking(tipo, periodo);
        return ResponseEntity.ok(ranking);
    }
}