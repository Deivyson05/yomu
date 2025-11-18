package br.com.yomu.gamificacaoDaLeitura.model;

import br.com.yomu.gamificacaoDaLeitura.model.enuns.TipoProgresso;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "progressos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class Progress {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "Livro Id é obrigatório.")
    @Column(name = "livro_id", nullable = false)
    private UUID livroId;

    @NotNull(message = "Usuário Id é obrigatório.")
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    @NotNull(message = "Quantidade é obrigatório.")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull(message = "Tipo de progresso é obrigatório.")
    @Column(name = "tipo_progresso_id", nullable = false)
    private Long tipoProgressoId;

    @Min(value = 0, message = "O XP não pode ser negativo.")
    @NotNull(message = "Xp Gerado é obrigatório.")
    @Column(name = "xp_gerado", nullable = false)
    private Integer xpGerado;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dataRegistro;

    @PrePersist
    private void calcularXp() {
        if (this.tipoProgressoId != null && this.quantidade != null) {
            TipoProgresso tipo = TipoProgresso.fromId(this.tipoProgressoId);
            this.xpGerado = tipo.calcularXp(this.quantidade);
        }
        validarRegrasNegocio();
    }

    private void validarRegrasNegocio() {
        if (this.quantidade == null || this.quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
    }

    @Transient
    public TipoProgresso getTipoProgresso() {
        return TipoProgresso.fromId(this.tipoProgressoId);
    }
}
