package br.com.yomu.gamificacaoDaLeitura.model;

import br.com.yomu.gamificacaoDaLeitura.model.enums.StatusAmizade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "amizades",
    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id_1", "usuario_id_2"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa a relação de amizade entre dois usuários")
public class Amizade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID único da amizade", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id_1", nullable = false)
    @JsonIgnore
    @Schema(description = "Primeiro usuário envolvido na amizade", hidden = true)
    private Usuario usuarioId1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id_2", nullable = false)
    @JsonIgnore
    @Schema(description = "Segundo usuário envolvido na amizade", hidden = true)
    private Usuario usuarioId2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Status da amizade", example = "PENDENTE")
    private StatusAmizade status = StatusAmizade.PENDENTE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "Data em que a solicitação foi feita", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataSolicitacao;

    @Schema(description = "Data em que a solicitação foi aceita", example = "2025-01-18T10:30:00")
    private LocalDateTime dataAceite;

    @PrePersist
    @PreUpdate
    public void validar() {
        if (usuarioId1.getId().equals(usuarioId2.getId())) {
            throw new IllegalArgumentException("Usuário não pode adicionar a si mesmo");
        }
    }
}
