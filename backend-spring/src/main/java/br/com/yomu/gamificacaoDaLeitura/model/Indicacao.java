package br.com.yomu.gamificacaoDaLeitura.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "indicacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Indicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remetente_id", nullable = false)
    @JsonIgnore
    private Usuario remetente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id", nullable = false)
    @JsonIgnore
    private Usuario destinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    @JsonIgnore
    private Livro livro;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @Column(nullable = false)
    private Boolean lida = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PreUpdate
    @PrePersist
    public void validar() {
        if (remetente.getId().equals(destinatario.getId())) {
            throw new IllegalArgumentException("Remetente e destinatário devem ser diferentes");
        }
    }
}