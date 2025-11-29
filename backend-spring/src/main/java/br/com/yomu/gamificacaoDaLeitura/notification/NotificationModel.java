package br.com.yomu.gamificacaoDaLeitura.notification;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;

@Data
@Entity
@Table(name = "notificacoes")
public class NotificationModel {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "text")
    private String mensagem;

    @Column(nullable = false)
    private boolean lido = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();
}
