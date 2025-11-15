package com.yomu.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


 //  Entidade Indicação 

public class Indicacao {
    
    private String id;
    private String usuarioIndicadorId;
    private String usuarioIndicadoId;
    private String livroId;
    private String mensagem;
    private StatusIndicacao status;
    private LocalDateTime dataIndicacao;
    private LocalDateTime dataResposta;
    
    public Indicacao() {
        this.id = UUID.randomUUID().toString();
        this.dataIndicacao = LocalDateTime.now();
        this.status = StatusIndicacao.PENDENTE;
    }
    
    public Indicacao(String usuarioIndicadorId, String usuarioIndicadoId, 
                     String livroId, String mensagem) {
        this();
        this.usuarioIndicadorId = usuarioIndicadorId;
        this.usuarioIndicadoId = usuarioIndicadoId;
        this.livroId = livroId;
        this.mensagem = mensagem;
    }
    
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
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
    
    public StatusIndicacao getStatus() { return status; }
    public void setStatus(StatusIndicacao status) { this.status = status; }
    
    public LocalDateTime getDataIndicacao() { return dataIndicacao; }
    public void setDataIndicacao(LocalDateTime dataIndicacao) { 
        this.dataIndicacao = dataIndicacao; 
    }
    
    public LocalDateTime getDataResposta() { return dataResposta; }
    public void setDataResposta(LocalDateTime dataResposta) { 
        this.dataResposta = dataResposta; 
    }
    
    // Métodos de negócio
    public void aceitar() {
        this.status = StatusIndicacao.ACEITA;
        this.dataResposta = LocalDateTime.now();
    }
    
    public void recusar() {
        this.status = StatusIndicacao.RECUSADA;
        this.dataResposta = LocalDateTime.now();
    }
    
    public boolean isPendente() {
        return this.status == StatusIndicacao.PENDENTE;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indicacao that = (Indicacao) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    public enum StatusIndicacao {
        PENDENTE, ACEITA, RECUSADA
    }
}