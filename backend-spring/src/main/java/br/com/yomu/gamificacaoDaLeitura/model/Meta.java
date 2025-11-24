package br.com.yomu.gamificacaoDaLeitura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoMeta;
import br.com.yomu.gamificacaoDaLeitura.model.enums.UnidadeMeta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "metas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa uma meta definida pelo usuário")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID único da meta", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    @Schema(description = "Usuário dono da meta", hidden = true)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Tipo da meta", example = "SEMANA")
    private TipoMeta tipoMeta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Unidade da meta (PAGINAS, CAPITULOS, LIVROS)", example = "PAGINAS")
    private UnidadeMeta unidadeMeta;

    @Min(1)
    @Column(nullable = false)
    @Schema(description = "Quantidade alvo para concluir a meta", example = "100")
    private Integer quantidadeAlvo;

    @Min(0)
    @Column(nullable = false)
    @Schema(description = "Quantidade atual de progresso", example = "0")
    private Integer quantidadeAtual = 0;

    @Column(nullable = false)
    @Schema(description = "Data de início da meta", example = "2025-01-20T10:30:00")
    private LocalDateTime dataInicio;

    @Column(nullable = false)
    @Schema(description = "Data de fim prevista da meta", example = "2025-01-27T10:30:00")
    private LocalDateTime dataFim;

    @Column(nullable = false)
    @Schema(description = "Se a meta já foi concluída", example = "false")
    private Boolean concluida = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @PrePersist
    public void calcularDataFim() {
        if (this.dataInicio != null && this.tipoMeta != null) {
            this.dataFim = this.dataInicio.plusDays(this.tipoMeta.getDuracaoDias());
        }
    }

    public void atualizarProgresso(Integer quantidade) {
        this.quantidadeAtual += quantidade;
        if (this.quantidadeAtual >= this.quantidadeAlvo) {
            this.concluida = true;
        }
    }
}