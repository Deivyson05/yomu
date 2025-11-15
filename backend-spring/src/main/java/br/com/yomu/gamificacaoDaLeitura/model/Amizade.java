package com.yomu.entity;

import com.yomu.enums.StatusAmizade;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "amizade")
public class Amizade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "usuario_id1", nullable = false)
    private Integer usuarioId1;
    
    @Column(name = "usuario_id2", nullable = false)
    private Integer usuarioId2;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status_amizade", nullable = false)
    private StatusAmizade statusAmizade = StatusAmizade.PENDENTE;
    
    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao = LocalDateTime.now();
    
    @Column(name = "data_aceitacao")
    private LocalDateTime dataAceitacao;
    
    @Column(name = "notificacoes_ativas")
    private Boolean notificacoesAtivas = true;
    
    @Column(name = "mensagem_solicitacao", length = 500)
    private String mensagemSolicitacao;
    
    @Column(name = "eh_favorito")
    private Boolean ehFavorito = false;
    
    // Relacionamentos com Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id1", insertable = false, updatable = false)
    private Usuario usuario1;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id2", insertable = false, updatable = false)
    private Usuario usuario2;
    
    // Construtores
    public Amizade() {}
    
    public Amizade(Integer usuarioId1, Integer usuarioId2) {
        // Garante que usuarioId1 sempre seja menor que usuarioId2
        this.usuarioId1 = Math.min(usuarioId1, usuarioId2);
        this.usuarioId2 = Math.max(usuarioId1, usuarioId2);
        this.dataSolicitacao = LocalDateTime.now();
        this.statusAmizade = StatusAmizade.PENDENTE;
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUsuarioId1() {
        return usuarioId1;
    }
    
    public void setUsuarioId1(Integer usuarioId1) {
        this.usuarioId1 = usuarioId1;
    }
    
    public Integer getUsuarioId2() {
        return usuarioId2;
    }
    
    public void setUsuarioId2(Integer usuarioId2) {
        this.usuarioId2 = usuarioId2;
    }
    
    public StatusAmizade getStatusAmizade() {
        return statusAmizade;
    }
    
    public void setStatusAmizade(StatusAmizade statusAmizade) {
        this.statusAmizade = statusAmizade;
    }
    
    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }
    
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
    
    public LocalDateTime getDataAceitacao() {
        return dataAceitacao;
    }
    
    public void setDataAceitacao(LocalDateTime dataAceitacao) {
        this.dataAceitacao = dataAceitacao;
    }
    
    public Boolean getNotificacoesAtivas() {
        return notificacoesAtivas;
    }
    
    public void setNotificacoesAtivas(Boolean notificacoesAtivas) {
        this.notificacoesAtivas = notificacoesAtivas;
    }
    
    public String getMensagemSolicitacao() {
        return mensagemSolicitacao;
    }
    
    public void setMensagemSolicitacao(String mensagemSolicitacao) {
        this.mensagemSolicitacao = mensagemSolicitacao;
    }
    
    public Boolean getEhFavorito() {
        return ehFavorito;
    }
    
    public void setEhFavorito(Boolean ehFavorito) {
        this.ehFavorito = ehFavorito;
    }
    
    public Usuario getUsuario1() {
        return usuario1;
    }
    
    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }
    
    public Usuario getUsuario2() {
        return usuario2;
    }
    
    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }
    
    // Métodos de negócio
    public void aceitar() {
        this.statusAmizade = StatusAmizade.ACEITA;
        this.dataAceitacao = LocalDateTime.now();
    }
    
    public void recusar() {
        this.statusAmizade = StatusAmizade.RECUSADA;
    }
    
    public void bloquear() {
        this.statusAmizade = StatusAmizade.BLOQUEADA;
    }
    
    public boolean isAceita() {
        return this.statusAmizade == StatusAmizade.ACEITA;
    }
    
    public boolean isPendente() {
        return this.statusAmizade == StatusAmizade.PENDENTE;
    }
    
    public boolean isBloqueada() {
        return this.statusAmizade == StatusAmizade.BLOQUEADA;
    }
    
    // Método para verificar se um usuário faz parte desta amizade
    public boolean contemUsuario(Integer usuarioId) {
        return this.usuarioId1.equals(usuarioId) || this.usuarioId2.equals(usuarioId);
    }
    
    // Método para obter o ID do outro usuário da amizade
    public Integer getOutroUsuarioId(Integer usuarioId) {
        if (this.usuarioId1.equals(usuarioId)) {
            return this.usuarioId2;
        } else if (this.usuarioId2.equals(usuarioId)) {
            return this.usuarioId1;
        }
        throw new IllegalArgumentException("Usuário não faz parte desta amizade");
    }
}
