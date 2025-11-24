package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Indicacao;
import br.com.yomu.gamificacaoDaLeitura.service.IndicacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/indicacoes")
@RequiredArgsConstructor
@Tag(name = "Indicações", description = "Sistema de recomendação de livros entre usuários")
public class IndicacaoController {

    private final IndicacaoService indicacaoService;

    @PostMapping
    @Operation(summary = "Criar indicação de livro", 
               description = "Permite que um usuário indique um livro para outro usuário, criando uma notificação automática")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Indicação criada com sucesso",
            content = @Content(schema = @Schema(implementation = Indicacao.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou tentativa de auto-indicação",
            content = @Content)
    })
    public ResponseEntity<Indicacao> criar(
            @Parameter(description = "ID do usuário que está indicando") @RequestParam UUID remetenteId,
            @Parameter(description = "ID do usuário que receberá a indicação") @RequestParam UUID destinatarioId,
            @Parameter(description = "ID do livro sendo indicado") @RequestParam UUID livroId,
            @Parameter(description = "Mensagem personalizada (opcional)") @RequestParam(required = false) String mensagem) {
        
        Indicacao indicacao = indicacaoService.criar(remetenteId, destinatarioId, livroId, mensagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(indicacao);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar indicação por ID", description = "Retorna os detalhes completos de uma indicação específica")
    public ResponseEntity<Indicacao> buscarPorId(
            @Parameter(description = "UUID da indicação") @PathVariable UUID id) {
        Indicacao indicacao = indicacaoService.buscarPorId(id);
        return ResponseEntity.ok(indicacao);
    }

    @GetMapping("/destinatario/{destinatarioId}")
    @Operation(summary = "Listar indicações recebidas", 
               description = "Retorna todas as indicações que um usuário recebeu, ordenadas da mais recente para a mais antiga")
    public ResponseEntity<List<Indicacao>> listarPorDestinatario(
            @Parameter(description = "UUID do destinatário") @PathVariable UUID destinatarioId) {
        List<Indicacao> indicacoes = indicacaoService.listarPorDestinatario(destinatarioId);
        return ResponseEntity.ok(indicacoes);
    }

    @GetMapping("/destinatario/{destinatarioId}/nao-lidas")
    @Operation(summary = "Listar indicações não lidas", 
               description = "Retorna apenas as indicações que o usuário ainda não visualizou")
    public ResponseEntity<List<Indicacao>> listarNaoLidas(
            @Parameter(description = "UUID do destinatário") @PathVariable UUID destinatarioId) {
        List<Indicacao> indicacoes = indicacaoService.listarNaoLidas(destinatarioId);
        return ResponseEntity.ok(indicacoes);
    }

    @GetMapping("/remetente/{remetenteId}")
    @Operation(summary = "Listar indicações enviadas", 
               description = "Retorna todas as indicações que um usuário enviou para outros")
    public ResponseEntity<List<Indicacao>> listarPorRemetente(
            @Parameter(description = "UUID do remetente") @PathVariable UUID remetenteId) {
        List<Indicacao> indicacoes = indicacaoService.listarPorRemetente(remetenteId);
        return ResponseEntity.ok(indicacoes);
    }

    @GetMapping("/destinatario/{destinatarioId}/contador")
    @Operation(summary = "Contar indicações não lidas", 
               description = "Retorna a quantidade de indicações não lidas para exibir badge de notificação")
    public ResponseEntity<Long> contarNaoLidas(
            @Parameter(description = "UUID do destinatário") @PathVariable UUID destinatarioId) {
        long contador = indicacaoService.contarNaoLidas(destinatarioId);
        return ResponseEntity.ok(contador);
    }

    @PatchMapping("/{id}/marcar-lida")
    @Operation(summary = "Marcar indicação como lida", 
               description = "Marca uma indicação específica como visualizada")
    public ResponseEntity<Void> marcarComoLida(
            @Parameter(description = "UUID da indicação") @PathVariable UUID id) {
        indicacaoService.marcarComoLida(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/destinatario/{destinatarioId}/marcar-todas-lidas")
    @Operation(summary = "Marcar todas como lidas", 
               description = "Marca todas as indicações de um usuário como visualizadas")
    public ResponseEntity<Void> marcarTodasComoLidas(
            @Parameter(description = "UUID do destinatário") @PathVariable UUID destinatarioId) {
        indicacaoService.marcarTodasComoLidas(destinatarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar indicação", 
               description = "Remove permanentemente uma indicação do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Indicação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Indicação não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID da indicação") @PathVariable UUID id) {
        indicacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}