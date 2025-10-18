package br.com.yomu.gamificacaoDaLeitura.model;

import org.hibernate.annotations.Immutable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "progressos") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor

@Immutable
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(mensagem = "Livro Id é obrigatório.")
    @Column(name = "livro_id", unique = true, nullable = false)
    private UUID id livroId;

    @NotNull(mensagem = "Usuário Id é obrigatório.")
    @Column(name = "usuario_id", unique = true, nullable = false)
    private UUID id usuarioId;

    @Min(value = 1, mensagem = "A quantidade deve ser maior que zero.")
    @NotNull(mensagem = "Quantidade é obrigatório.")
    @Column(name = "quantidade", unique = false, nullable = false)
    private int quantidade;

    @NotNull(mensagem = "Tipo de progresso é obrigatório.")
    @Column(name = "tipo_progresso_id", nullable = false)
    private long tipoProgressoId;

    @Min(value = 0, mensagem = "O XP não pode ser negativo.")
    @NotNull(mensagem = "Xp Gerado é obrigatório.")
    @Column(name = "xp_gerado", nullable = false)
    private int xpGerado;

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
        if (quantidade != null && quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
    }

   
    @Transient
    public TipoProgresso getTipoProgresso() {
        return TipoProgresso.fromId(this.tipoProgressoId);
    }
}