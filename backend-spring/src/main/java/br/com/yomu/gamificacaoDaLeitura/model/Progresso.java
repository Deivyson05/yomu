package br.com.yomu.gamificacaoDaLeitura.model;

import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoProgresso;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "progressos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Progresso{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Melhora a performance faz com que o banco de dados gere o ID automaticamente
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY) // Só sera carregado quando for acessado
    @JoinColumn(name = "livro_id", nullable = false)
    @JsonIgnore
    private Livro livro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // garante que n pode ser nula e define a chave estrangeira
    @JsonIgnore
    private Usuario usuario;

    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProgresso tipoProgresso;

    @Min(value = 0, message = "XP gerado não pode ser negativo")
    @Column(nullable = false)
    private Long xpGerado;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void calcularXp() {
        if (this.tipoProgresso == TipoProgresso.PAGINA) {
            this.xpGerado = this.quantidade * 10L;
        } else if (this.tipoProgresso == TipoProgresso.CAPITULO) {
            this.xpGerado = this.quantidade * 50L;
        }
    }
}