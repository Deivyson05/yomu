package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Notificacao;
import br.com.yomu.gamificacaoDaLeitura.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
@Tag(name = "Notificações", description = "Gerenciamento das notificações enviadas aos usuários")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @Operation(summary = "Listar notificações de um usuário", description = "Retorna todas as notificações pertencentes ao usuário especificado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacao>> listarPorUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {
        List<Notificacao> notificacoes = notificacaoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @Operation(summary = "Listar notificações não lidas", description = "Retorna todas as notificações não lidas de um usuário.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de notificações não lidas retornada com sucesso")
    })
    @GetMapping("/usuario/{usuarioId}/nao-lidas")
    public ResponseEntity<List<Notificacao>> listarNaoLidas(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {
        List<Notificacao> notificacoes = notificacaoService.listarNaoLidas(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @Operation(summary = "Contar notificações não lidas", description = "Retorna o total de notificações não lidas de um usuário.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contagem de notificações retornada com sucesso")
    })
    @GetMapping("/usuario/{usuarioId}/contador")
    public ResponseEntity<Long> contarNaoLidas(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {
        long contador = notificacaoService.contarNaoLidas(usuarioId);
        return ResponseEntity.ok(contador);
    }

    @Operation(summary = "Marcar notificação como lida", description = "Define o status de uma notificação específica como lida.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Notificação marcada como lida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    })
    @PatchMapping("/{id}/marcar-lida")
    public ResponseEntity<Void> marcarComoLida(
            @Parameter(description = "ID da notificação", required = true)
            @PathVariable UUID id) {
        notificacaoService.marcarComoLida(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Marcar todas as notificações como lidas", description = "Marca todas as notificações de um usuário como lidas.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Todas as notificações foram marcadas como lidas")
    })
    @PatchMapping("/usuario/{usuarioId}/marcar-todas-lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {
        notificacaoService.marcarTodasComoLidas(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar notificação", description = "Remove uma notificação específica do sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Notificação deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da notificação", required = true)
            @PathVariable UUID id) {
        notificacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
