package com.yomu.controller;

import com.yomu.model.Indicacao;
import com.yomu.service.IndicacaoService;
import com.yomu.exception.IndicacaoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/indicacoes")
public class IndicacaoController {
    
    private final IndicacaoService service;
    
    public IndicacaoController(IndicacaoService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<Indicacao> criar(@RequestBody IndicacaoRequest request) {
        try {
            Indicacao indicacao = service.criar(
                request.getUsuarioIndicadorId(),
                request.getUsuarioIndicadoId(),
                request.getLivroId(),
                request.getMensagem()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(indicacao);
        } catch (IndicacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Indicacao> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Indicacao>> listarPorUsuario(@PathVariable String usuarioId) {
        List<Indicacao> indicacoes = service.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(indicacoes);
    }
    
    @GetMapping("/usuario/{usuarioId}/pendentes")
    public ResponseEntity<List<Indicacao>> listarPendentes(@PathVariable String usuarioId) {
        List<Indicacao> pendentes = service.listarPendentes(usuarioId);
        return ResponseEntity.ok(pendentes);
    }
    
    @PutMapping("/{id}/aceitar")
    public ResponseEntity<Indicacao> aceitar(@PathVariable String id, 
                                             @RequestParam String usuarioId) {
        try {
            Indicacao indicacao = service.aceitar(id, usuarioId);
            return ResponseEntity.ok(indicacao);
        } catch (IndicacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/recusar")
    public ResponseEntity<Indicacao> recusar(@PathVariable String id, 
                                             @RequestParam String usuarioId) {
        try {
            Indicacao indicacao = service.recusar(id, usuarioId);
            return ResponseEntity.ok(indicacao);
        } catch (IndicacaoException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    // DTO interno para requests
    public static class IndicacaoRequest {
        private String usuarioIndicadorId;
        private String usuarioIndicadoId;
        private String livroId;
        private String mensagem;
        
        public String getUsuarioIndicadorId() { return usuarioIndicadorId; }
        public void setUsuarioIndicadorId(String usuarioIndicadorId) { 
            this.usuarioIndicadorId = usuarioIndicadorId; 
        }
        
        public String getUsuarioIndicadoId() { return usuarioIndicadoId; }
        public void setUsuarioIndicadoId(String usuarioIndicadoId) { 
            this.usuarioIndicadoId = usuarioIndicadoId; 
        }
        
        public String getLivroId() { return livroId; }
        public void setLivroId(String livroId) { this.livroId = livroId; }
        
        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    }
}