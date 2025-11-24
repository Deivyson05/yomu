package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Livro;
import br.com.yomu.gamificacaoDaLeitura.service.LivroService;
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
@RequestMapping("/api/livros")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "Endpoints para gerenciamento dos livros do usuário")
public class LivroController {

    private final LivroService livroService;

    @PostMapping("/usuario/{usuarioId}")
    @Operation(
            summary = "Criar um novo livro",
            description = "Cria um novo livro vinculado a um usuário existente. "
                        + "Campos como id, usuario, createdAt e updatedAt são gerados automaticamente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Livro criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Livro.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de criação de livro (registro por páginas)",
                                            value = """
                                            {
                                                "titulo": "O Senhor dos Anéis",
                                                "autor": "J. R. R. Tolkien",
                                                "numeroPaginas": 1200,
                                                "numeroCapitulos": 62,
                                                "capa": "https://images.pexels.com/photos/22969/pexels-photo.jpg",
                                                "descricao": "Uma fantasia épica sobre a jornada do Um Anel.",
                                                "tipoRegistro": "PAGINA",
                                                "finalizado": false
                                            }
                                            """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos. Verifique campos obrigatórios."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado"
            )
    })
    public ResponseEntity<Livro> criar(
            @Parameter(description = "ID do usuário ao qual o livro será vinculado", required = true)
            @PathVariable UUID usuarioId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto Livro contendo os dados para criação (sem ID)",
                    required = true
            )
            @Valid @RequestBody Livro livro) {

        Livro criado = livroService.criar(usuarioId, livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // =============== GET BY ID ==================================

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro encontrado",
                    content = @Content(schema = @Schema(implementation = Livro.class))
            ),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Livro> buscarPorId(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable UUID id) {

        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    // =============== GET BY USER ===============================

    @GetMapping("/usuario/{usuarioId}")
    @Operation(
            summary = "Listar livros de um usuário",
            description = "Retorna uma lista de livros cadastrados por um usuário específico."
    )
    public ResponseEntity<List<Livro>> listarPorUsuario(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId) {

        return ResponseEntity.ok(livroService.listarPorUsuario(usuarioId));
    }

    // =============== GET BY FINALIZADO ==========================
   

    @GetMapping("/usuario/{usuarioId}/finalizado/{finalizado}")
    @Operation(
            summary = "Listar livros por status de finalização",
            description = "Retorna os livros de um usuário filtrando por finalizado (true) ou não finalizado (false)."
    )
    public ResponseEntity<List<Livro>> listarPorStatus(
            @Parameter(description = "ID do usuário", required = true)
            @PathVariable UUID usuarioId,

            @Parameter(description = "Define se o filtro deve retornar livros finalizados (true) ou não finalizados (false)")
            @PathVariable Boolean finalizado) {

        return ResponseEntity.ok(livroService.listarPorUsuarioEStatus(usuarioId, finalizado));
    }

    // =============== PUT - ATUALIZAR COMPLETO ==================

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar dados de um livro",
            description = "Atualiza os dados de um livro existente. Campos não enviados permanecem iguais."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Livro> atualizar(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable UUID id,

            @Valid @RequestBody Livro livro) {

        return ResponseEntity.ok(livroService.atualizar(id, livro));
    }

    // =============== PATCH - FINALIZAR ==========================

    @PatchMapping("/{id}/finalizar")
    @Operation(summary = "Marcar livro como finalizado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro finalizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Void> marcarComoFinalizado(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable UUID id) {

        livroService.marcarComoFinalizado(id);
        return ResponseEntity.noContent().build();
    }

    // =============== DELETE ====================================

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um livro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro excluído"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable UUID id) {

        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
