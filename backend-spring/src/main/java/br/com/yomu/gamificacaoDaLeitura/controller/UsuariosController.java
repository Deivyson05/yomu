package br.com.yomu.gamificacaoDaLeitura.controller;

// Importa o modelo de dados e o serviço responsável pela lógica de negócio
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.service.UsuariosService;

// Importações do Spring para lidar com injeção de dependência e respostas HTTP
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Importa classes utilitárias
import java.util.List;
import java.util.UUID;

/**
 * Controlador REST responsável por gerenciar os endpoints da entidade "Usuario".
 * Ele expõe rotas HTTP (GET, POST, PUT, DELETE, PATCH) que interagem com o serviço UsuariosService.
 */
@RestController
@RequestMapping("/api") // Define o prefixo padrão para todas as rotas do controlador
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (importante para integração com front-end)
public class UsuariosController {

    // Injeção automática da camada de serviço, que contém a lógica de negócio
    @Autowired
    private UsuariosService usuariosService;

    /**
     * Cria um novo usuário no sistema.
     */
    @PostMapping("/criarUsuario")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
            // Chama o serviço para criar o usuário e retorna o objeto criado
            Usuario novoUsuario = usuariosService.criarUsuario(usuario);
            // Retorna status HTTP 201 (Created) com o usuário no corpo da resposta
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (IllegalArgumentException e) {
            // Caso haja erro de validação, retorna 400 (Bad Request)
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lista todos os usuários cadastrados.
     */
    @GetMapping("/listarTodos")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuariosService.listarTodos();
        // Retorna 200 (OK) com a lista de usuários
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Busca um usuário pelo seu ID (UUID).
     */
    @GetMapping("/buscarUsuarioPorId/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable UUID id) {
        try {
            Usuario usuario = usuariosService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            // Retorna 404 (Not Found) se o usuário não existir
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca um usuário pelo nome de usuário.
     */
    @GetMapping("/buscarUsuarioPorNome/{nomeUsuario}")
    public ResponseEntity<Usuario> buscarPorNomeUsuario(@PathVariable String nomeUsuario) {
        try {
            Usuario usuario = usuariosService.buscarPorNomeUsuario(nomeUsuario);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca um usuário através de um código de convite (possivelmente usado para registro ou vinculação).
     */
    @GetMapping("/buscarUsuarioPorCodigoConvite/{codigoConvite}")
    public ResponseEntity<Usuario> buscarPorCodigoConvite(@PathVariable String codigoConvite) {
        try {
            Usuario usuario = usuariosService.buscarPorCodigoConvite(codigoConvite);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza os dados de um usuário existente.
     */
    @PutMapping("/atualizarUsuario/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuariosService.atualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            // Retorna 400 caso os dados sejam inválidos ou o usuário não exista
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Adiciona pontos de experiência (XP) a um usuário específico.
     */
    @PatchMapping("/adicionarXP/{id}/xp")
    public ResponseEntity<Usuario> adicionarXp(@PathVariable UUID id, @RequestParam int xp) {
        try {
            Usuario usuario = usuariosService.adicionarXp(id, xp);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deleta um usuário pelo seu ID.
     */
    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        try {
            usuariosService.deletarUsuario(id);
            // Retorna 204 (No Content) indicando sucesso sem corpo de resposta
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // Caso o usuário não exista
            return ResponseEntity.notFound().build();
        }
    }
}