package br.com.yomu.gamificacaoDaLeitura.model;

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

/**
 * Entidade que representa a tabela "usuarios" no banco de dados.
 * 
 * Esta classe mapeia os atributos do usuário e utiliza:
 *  - JPA para persistência
 *  - Lombok para gerar automaticamente getters, setters e construtores
 *  - Hibernate para controle automático de timestamps
 */
@Entity // Indica que esta classe é uma entidade gerenciada pelo JPA
@Table(name = "usuarios") // Define o nome da tabela no banco
@Data // Lombok: gera automaticamente getters, setters, toString, equals e hashCode
@NoArgsConstructor // Lombok: cria um construtor vazio
@AllArgsConstructor // Lombok: cria um construtor com todos os campos
public class Usuario {
    // Identificador único (chave primária)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Gera automaticamente um UUID como ID
    private UUID id;

    // Nome de usuário — deve ser único e não nulo
    @Column(name = "nome_usuario", unique = true, nullable = false, length = 50)
    private String nomeUsuario;

    // E-mail do usuário — também deve ser único e obrigatório
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // Senha criptografada ou em texto simples (depende da implementação da camada de serviço)
    @Column(nullable = false)
    private String senha;

    // Quantidade total de XP acumulado pelo usuário
    @Column(name = "xp_total", nullable = false)
    private Integer xpTotal = 0;

    // Nível atual do usuário dentro do sistema (começa em 1)
    @Column(name = "nivel_atual", nullable = false)
    private Integer nivelAtual = 1;

    // Código de convite único gerado automaticamente (usado para compartilhar convites ou referidos)
    @Column(name = "codigo_convite", unique = true, nullable = false, length = 20)
    private String codigoConvite;

    // URL ou caminho da imagem de perfil do usuário (opcional)
    @Column(name = "foto_perfil", length = 500)
    private String fotoPerfil;

    // Timestamp gerado automaticamente na criação do registro
    @CreationTimestamp // Hibernate: preenche automaticamente com a data/hora atual na inserção
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Timestamp atualizado automaticamente a cada modificação
    @UpdateTimestamp // Hibernate: atualiza automaticamente na modificação
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Método executado automaticamente antes de salvar (persistir) a entidade.
     * 
     * Funções:
     *  - Gera um código de convite aleatório (UUID truncado)
     *  - Garante que xpTotal e nivelAtual tenham valores padrão se estiverem nulos
     */
    @PrePersist
    public void prePersist() {
        if (this.codigoConvite == null || this.codigoConvite.isEmpty()) {
            this.codigoConvite = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        if (this.xpTotal == null) {
            this.xpTotal = 0;
        }
        if (this.nivelAtual == null) {
            this.nivelAtual = 1;
        }
    }
}