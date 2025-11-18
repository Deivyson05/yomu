package br.com.yomu.gamificacaoDaLeitura.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "indicacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Indicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID usuarioIndicadorId;

    @Column(nullable = false)
    private UUID usuarioIndicadoId;

    @Column(nullable = false)
    private String livroId;

    @Column(nullable = false, length = 500)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusIndicacao status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        dataCriacao = LocalDateTime.now();
        status = StatusIndicacao.PENDENTE;
    }
}
