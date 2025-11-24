package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários e perfis")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ====== records inline p/ Swagger ======
    @Schema(name = "LoginBody", description = "Credenciais para login (estudo)")
    public static record LoginBody(
            @Schema(example = "joao@email.com", required = true) String email,
            @Schema(example = "senha123", required = true) String senha
    ) {}

    @Schema(name = "LoginResult", description = "Resposta do login")
    public static record LoginResult(
            @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id,
            @Schema(example = "joao_silva") String nomeUsuario,
            @Schema(example = "joao@email.com") String email,
            @Schema(example = "https://example.com/foto.jpg") String fotoPerfil,
            @Schema(example = "Login efetuado com sucesso") String message
    ) {}

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @Operation(
        summary = "Login por e-mail e senha",
        description = "Autentica o usuário pelo e-mail e senha e retorna dados básicos do perfil.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login OK",
                content = @Content(schema = @Schema(implementation = LoginResult.class))),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                content = @Content(schema = @Schema(implementation = String.class)))
        }
    )
    public ResponseEntity<?> login(@Valid @RequestBody LoginBody body) {
        try {
            var u = usuarioService.login(body.email(), body.senha());
            return ResponseEntity.ok(new LoginResult(
                    u.getId(),
                    u.getNomeUsuario(),
                    u.getEmail(),
                    u.getFotoPerfil(),
                    "Login efetuado com sucesso"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Email ou senha inválidos");
        }
    }

    @PostMapping
    @GetMapping("/Criar")
    @Operation(summary = "Criar novo usuário", description = "Cadastra um novo usuário no sistema com geração automática de código de convite")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou email/username já cadastrado",
            content = @Content)
    })
    public ResponseEntity<Usuario> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário a ser criado",
            required = true,
            content = @Content(
                schema = @Schema(
                    example = "{\n" +
                              "  \"nomeUsuario\": \"joao_silva\",\n" +
                              "  \"email\": \"joao@email.com\",\n" +
                              "  \"senha\": \"senha123\"\n" +
                              "}"
                )
            )
        )
        @Valid @RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados completos de um usuário pelo seu UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
            content = @Content)
    })
    public ResponseEntity<Usuario> buscarPorId(
            @Parameter(description = "UUID do usuário", required = true)
            @PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email", description = "Retorna um usuário pelo seu endereço de email")
    public ResponseEntity<Usuario> buscarPorEmail(
            @Parameter(description = "Email do usuário", example = "joao@email.com")
            @PathVariable String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/convite/{codigo}")
    @Operation(summary = "Buscar usuário por código de convite", 
               description = "Usado para adicionar amigos através do código único de convite")
    public ResponseEntity<Usuario> buscarPorCodigoConvite(
            @Parameter(description = "Código de convite único (8 caracteres)", example = "ABC12345")
            @PathVariable String codigo) {
        Usuario usuario = usuarioService.buscarPorCodigoConvite(codigo);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna lista completa de usuários cadastrados")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza dados do perfil do usuário")
    public ResponseEntity<Usuario> atualizar(
            @Parameter(description = "UUID do usuário") @PathVariable UUID id,
            @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove permanentemente um usuário do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID do usuário") @PathVariable UUID id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}