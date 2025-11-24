package br.com.yomu.gamificacaoDaLeitura.model;

import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidade que representa um livro registrado pelo usuário")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID único do livro", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @NotBlank(message = "Título é obrigatório")
    @Schema(description = "Título do livro", example = "O Senhor dos Anéis")
    private String titulo;

    @NotBlank(message = "Autor é obrigatório")
    @Schema(description = "Autor do livro", example = "J. R. R. Tolkien")
    private String autor;

    @Min(0)
    @Schema(description = "Número total de páginas", example = "1200")
    private Integer numeroPaginas;

    @Min(0)
    @Schema(description = "Número total de capítulos", example = "62")
    private Integer numeroCapitulos;

    @Schema(description = "URL da imagem da capa", example = "https://images.site.com/capa.jpg")
    private String capa;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Descrição do livro", example = "Uma fantasia épica...")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Tipo de registro (página ou capítulo)", example = "PAGINA")
    private TipoRegistro tipoRegistro;

    @Column(nullable = false)
    @Schema(description = "Indica se o livro foi finalizado", example = "false")
    private Boolean finalizado = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Schema(description = "Data de criação", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    @Schema(description = "Última atualização", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(hidden = true)
    private List<Progresso> progressos;

    @OneToMany(mappedBy = "livro")
    @JsonIgnore
    @Schema(hidden = true)
    private List<Indicacao> indicacoes;
}
