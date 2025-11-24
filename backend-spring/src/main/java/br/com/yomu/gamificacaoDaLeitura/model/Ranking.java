package br.com.yomu.gamificacaoDaLeitura.model;

import br.com.yomu.gamificacaoDaLeitura.model.enums.PeriodoRanking;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoRanking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rankings",
       uniqueConstraints = @UniqueConstraint(columnNames = {"tipo_ranking", "periodo_ranking"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRanking tipoRanking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodoRanking periodoRanking;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private String dados;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}