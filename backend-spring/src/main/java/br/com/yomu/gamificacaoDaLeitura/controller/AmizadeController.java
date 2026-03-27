package com.yomu.controller;

import com.yomu.entity.Amizade;
import com.yomu.entity.Usuario;
import com.yomu.service.AmizadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/amizades")
@CrossOrigin(origins = "*")
public class AmizadeController {
    
    @Autowired
    private AmizadeService amizadeService;
    
    /**
     * POST /api/amizades - Solicitar amizade
     * Body: { "usuarioId1": 1, "usuarioId2": 2, "mensagem": "Olá!" }
     */
    @PostMapping
    public ResponseEntity<?> solicitarAmizade(@RequestBody Map<String, Object> payload) {
        try {
            Integer usuarioId1 = (Integer) payload.get("usuarioId1");
            Integer usuarioId2 = (Integer) payload.get("usuarioId2");
            String mensagem = (String) payload.get("mensagem");
            
            Amizade amizade = amizadeService.solicitarAmizade(usuarioId1, usuarioId2, mensagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(amizade);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erro", "Erro ao solicitar amizade"));
        }
    }
    
    /**
     * PUT /api/amizades/{id}/aceitar - Aceitar solicitação
     * Params: usuarioId (quem está aceitando)
     */
    @PutMapping("/{id}/aceitar")
    public ResponseEntity<?> aceitarAmizade(
            @PathVariable Integer id,
            @RequestParam Integer usuarioId) {
        try {
            Amizade amizade = amizadeService.aceitarAmizade(id, usuarioId);
            return ResponseEntity.ok(amizade);
        } catch (RuntimeException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    /**
     * PUT /api/amizades/{id}/recusar - Recusar solicitação
     */
    @PutMapping("/{id}/recusar")
    public ResponseEntity<?> recusarAmizade(
            @PathVariable Integer id,
            @RequestParam Integer usuarioId) {
        try {
            amizadeService.recusarAmizade(id, usuarioId);
            return ResponseEntity.ok(Map.of("mensagem", "Solicitação recusada com sucesso"));
        } catch (RuntimeException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    /**
     * DELETE /api/amizades/{id} - Remover amizade
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerAmizade(
            @PathVariable Integer id,
            @RequestParam Integer usuarioId) {
        try {
            amizadeService.removerAmizade(id, usuarioId);
            return ResponseEntity.ok(Map.of("mensagem", "Amizade removida com sucesso"));
        } catch (RuntimeException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    /**
     * POST /api/amizades/bloquear - Bloquear usuário
     */
    @PostMapping("/bloquear")
    public ResponseEntity<?> bloquearUsuario(@RequestBody Map<String, Integer> payload) {
        try {
            Integer usuarioId1 = payload.get("usuarioId1");
            Integer usuarioId2 = payload.get("usuarioId2");
            
            Amizade amizade = amizadeService.bloquearUsuario(usuarioId1, usuarioId2);
            return ResponseEntity.ok(amizade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    /**
     * PUT /api/amizades/{id}/favorito - Marcar/desmarcar como favorito
     */
    @PutMapping("/{id}/favorito")
    public ResponseEntity<?> alternarFavorito(
            @PathVariable Integer id,
            @RequestParam Integer usuarioId) {
        try {
            Amizade amizade = amizadeService.alternarFavorito(id, usuarioId);
            return ResponseEntity.ok(amizade);
        } catch (RuntimeException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    /**
     * PUT /api/amizades/{id}/notificacoes - Ativar/desativar notificações
     */
    @PutMapping("/{id}/notificacoes")
    public ResponseEntity<?> alternarNotificacoes(
            @PathVariable Integer id,
            @RequestParam Integer usuarioId) {
        try {
            Amizade amizade = amizadeService.alternarNotificacoes(id, usuarioId);
            return ResponseEntity.ok(amizade);
        } catch (RuntimeException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    /**
     * GET /api/amizades/amigos/{usuarioId} - Listar amigos aceitos
     */
    @GetMapping("/amigos/{usuarioId}")
    public ResponseEntity<List<Usuario>> listarAmigos(@PathVariable Integer usuarioId) {
        List<Usuario> amigos = amizadeService.listarAmigos(usuarioId);
        return ResponseEntity.ok(amigos);
    }
    
    /**
     * GET /api/amizades/solicitacoes/recebidas/{usuarioId} - Solicitações recebidas
     */
    @GetMapping("/solicitacoes/recebidas/{usuarioId}")
    public ResponseEntity<List<Amizade>> listarSolicitacoesRecebidas(@PathVariable Integer usuarioId) {
        List<Amizade> solicitacoes = amizadeService.listarSolicitacoesRecebidas(usuarioId);
        return ResponseEntity.ok(solicitacoes);
    }
    
    /**
     * GET /api/amizades/solicitacoes/enviadas/{usuarioId} - Solicitações enviadas
     */
    @GetMapping("/solicitacoes/enviadas/{usuarioId}")
    public ResponseEntity<List<Amizade>> listarSolicitacoesEnviadas(@PathVariable Integer usuarioId) {
        List<Amizade> solicitacoes = amizadeService.listarSolicitacoesEnviadas(usuarioId);
        return ResponseEntity.ok(solicitacoes);
    }
    
    /**
     * GET /api/amizades/favoritos/{usuarioId} - Listar amigos favoritos
     */
    @GetMapping("/favoritos/{usuarioId}")
    public ResponseEntity<List<Usuario>> listarAmigosFavoritos(@PathVariable Integer usuarioId) {
        List<Usuario> favoritos = amizadeService.listarAmigosFavoritos(usuarioId);
        return ResponseEntity.ok(favoritos);
    }
    
    /**
     * GET /api/amizades/verificar?usuario1={id1}&usuario2={id2} - Verificar se são amigos
     */
    @GetMapping("/verificar")
    public ResponseEntity<Map<String, Boolean>> verificarAmizade(
            @RequestParam Integer usuario1,
            @RequestParam Integer usuario2) {
        boolean saoAmigos = amizadeService.saoAmigos(usuario1, usuario2);
        return ResponseEntity.ok(Map.of("saoAmigos", saoAmigos));
    }
    
    /**
     * GET /api/amizades/contar/{usuarioId} - Contar amigos
     */
    @GetMapping("/contar/{usuarioId}")
    public ResponseEntity<Map<String, Long>> contarAmigos(@PathVariable Integer usuarioId) {
        Long total = amizadeService.contarAmigos(usuarioId);
        return ResponseEntity.ok(Map.of("totalAmigos", total));
    }
    
    /**
     * GET /api/amizades/{id} - Buscar amizade por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAmizade(@PathVariable Integer id) {
        try {
            Amizade amizade = amizadeService
