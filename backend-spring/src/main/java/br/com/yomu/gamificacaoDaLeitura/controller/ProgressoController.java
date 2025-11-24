package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Progresso;
import br.com.yomu.gamificacaoDaLeitura.service.ProgressoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/progressos")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(
        name = "Progresso",
        description = "Endpoints relacionados ao progresso de leitura do usuário"
)
public class ProgressoController {

    private final ProgressoService progressoService;

    // =============== REGISTRAR PROGRESSO =========================
    @PostMapping("/usuario/{usuarioId}/livro/{livroId}")
    @Operation(
            summary = "Registrar progresso de leitura",
            description = "Registra páginas/capítulos lidos e calcula automaticamente o XP gerado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Progresso registrado com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = Progresso.class),
                            examples = @ExampleObject(
                                    name = "Exemplo registro de progresso",
                                    value = """
                                    {
                                        "quantidade": 5,
                                        "tipoProgresso": "PAGINA"
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário ou livro não encontrado")
    })
    public ResponseEntity<Progresso> registrar(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId,

            @Parameter(description = "ID do livro", required = true)
            @PathVariable UUID livroId,

            @Valid @RequestBody Progresso progresso) {

        Progresso progressoRegistrado = progressoService.registrar(usuarioId, livroId, progresso);
        return ResponseEntity.status(HttpStatus.CREATED).body(progressoRegistrado);
    }

    // =============== LISTAR POR USUARIO =========================
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar progressos por usuário")
    public ResponseEntity<List<Progresso>> listarPorUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        return ResponseEntity.ok(progressoService.listarPorUsuario(usuarioId));
    }

    // =============== LISTAR POR LIVRO =========================
    @GetMapping("/livro/{livroId}")
    @Operation(summary = "Listar progressos por livro")
    public ResponseEntity<List<Progresso>> listarPorLivro(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable UUID livroId) {

        return ResponseEntity.ok(progressoService.listarPorLivro(livroId));
    }

    // =============== LISTAR POR PERIODO =========================
    @GetMapping("/usuario/{usuarioId}/periodo")
    @Operation(summary = "Listar progressos por período")
    public ResponseEntity<List<Progresso>> listarPorPeriodo(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId,

            @Parameter(description = "Data inicial", example = "2025-01-01T00:00:00", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,

            @Parameter(description = "Data final", example = "2025-01-31T23:59:59", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        return ResponseEntity.ok(progressoService.listarPorPeriodo(usuarioId, inicio, fim));
    }

    // =============== CALCULAR XP TOTAL =========================
    @GetMapping("/usuario/{usuarioId}/xp-total")
    @Operation(summary = "Calcular XP total acumulado pelo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "XP total retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Long> calcularXpTotal(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        Long xpTotal = progressoService.calcularXpTotal(usuarioId);
        return ResponseEntity.ok(xpTotal);
    }
}
