package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Meta;
import br.com.yomu.gamificacaoDaLeitura.service.MetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/metas")
@RequiredArgsConstructor
@Tag(name = "Metas", description = "Endpoints para gerenciamento das metas de leitura do usuário")
public class MetaController {

    private final MetaService metaService;

    // =============== CRIAR META =================================

    @PostMapping("/usuario/{usuarioId}")
    @Operation(
            summary = "Criar uma nova meta",
            description = "Cria uma meta baseada em tipo, unidade e quantidade alvo. "
                        + "A data de início é setada automaticamente e a dataFim é calculada."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meta criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Meta.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de criação de meta semanal por páginas",
                                            value = """
                                                "tipoMeta": "SEMANA",
                                                "unidadeMeta": "PAGINAS",
                                                "quantidadeAlvo": 100
                                            }
                                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Meta> criar(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId,

            @Valid @RequestBody Meta meta) {

        Meta metaCriada = metaService.criar(usuarioId, meta);
        return ResponseEntity.status(HttpStatus.CREATED).body(metaCriada);
    }

    // =============== BUSCAR META POR ID ========================

    @GetMapping("/{id}")
    @Operation(summary = "Buscar meta por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meta encontrada"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    public ResponseEntity<Meta> buscarPorId(
            @Parameter(description = "ID da meta", required = true)
            @PathVariable UUID id) {

        Meta meta = metaService.buscarPorId(id);
        return ResponseEntity.ok(meta);
    }

    // =============== LISTAR POR USUÁRIO ========================

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar todas as metas de um usuário")
    public ResponseEntity<List<Meta>> listarPorUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        return ResponseEntity.ok(metaService.listarPorUsuario(usuarioId));
    }

    // =============== LISTAR ATIVAS =============================


    @GetMapping("/usuario/{usuarioId}/ativas")
    @Operation(summary = "Listar metas ativas de um usuário")
    public ResponseEntity<List<Meta>> listarMetasAtivas(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        return ResponseEntity.ok(metaService.listarMetasAtivas(usuarioId));
    }

    // =============== LISTAR CONCLUÍDAS =========================

    @GetMapping("/usuario/{usuarioId}/concluidas")
    @Operation(summary = "Listar metas concluídas de um usuário")
    public ResponseEntity<List<Meta>> listarMetasConcluidas(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        return ResponseEntity.ok(metaService.listarMetasConcluidas(usuarioId));
    }

    // =============== DELETE ====================================

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma meta pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meta excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da meta", required = true)
            @PathVariable UUID id) {

        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

