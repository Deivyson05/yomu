package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Amizade;
import br.com.yomu.gamificacaoDaLeitura.service.AmizadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/amizades")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "Amizades", description = "Endpoints para gerenciamento de amizades entre usuários")
public class AmizadeController {

    private final AmizadeService amizadeService;

    // =============== ENVIAR SOLICITACAO =========================
    @PostMapping("/solicitar")
    @Operation(
            summary = "Enviar solicitação de amizade",
            description = "Envia uma solicitação de amizade entre dois usuários distintos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Solicitação enviada com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Amizade.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "usuarioId1": "UUID do usuário solicitante",
                                        "usuarioId2": "UUID do usuário alvo"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Amizade> enviarSolicitacao(
            @Parameter(description = "ID do usuário solicitante", required = true)
            @RequestParam UUID usuarioId1,

            @Parameter(description = "ID do usuário a ser adicionado", required = true)
            @RequestParam UUID usuarioId2) {

        Amizade amizade = amizadeService.enviarSolicitacao(usuarioId1, usuarioId2);
        return ResponseEntity.status(HttpStatus.CREATED).body(amizade);
    }

    // =============== ACEITAR SOLICITACAO =========================
    @PatchMapping("/{id}/aceitar")
    @Operation(summary = "Aceitar solicitação de amizade")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitação aceita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Solicitação não encontrada")
    })
    public ResponseEntity<Amizade> aceitarSolicitacao(
            @Parameter(description = "ID da amizade/solicitação", required = true)
            @PathVariable UUID id) {

        return ResponseEntity.ok(amizadeService.aceitarSolicitacao(id));
    }

    // =============== BLOQUEAR AMIZADE =========================
    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário bloqueado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Amizade não encontrada")
    })
    public ResponseEntity<Void> bloquearUsuario(
            @Parameter(description = "ID da amizade", required = true)
            @PathVariable UUID id) {

        amizadeService.bloquearUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // =============== LISTAR SOLICITACOES PENDENTES =========================
    @GetMapping("/usuario/{usuarioId}/pendentes")
    @Operation(summary = "Listar solicitações pendentes")
    public ResponseEntity<List<Amizade>> listarSolicitacoesPendentes(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        return ResponseEntity.ok(amizadeService.listarSolicitacoesPendentes(usuarioId));
    }

    // =============== LISTAR AMIGOS =========================
    @GetMapping("/usuario/{usuarioId}/amigos")
    @Operation(summary = "Listar amigos aceitos")
    public ResponseEntity<List<Amizade>> listarAmigos(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        return ResponseEntity.ok(amizadeService.listarAmigos(usuarioId));
    }

    // =============== REMOVER AMIZADE =========================
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover amizade")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Amizade removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Amizade não encontrada")
    })
    public ResponseEntity<Void> removerAmizade(
            @Parameter(description = "ID da amizade", required = true)
            @PathVariable UUID id) {

        amizadeService.removerAmizade(id);
        return ResponseEntity.noContent().build();
    }
}
