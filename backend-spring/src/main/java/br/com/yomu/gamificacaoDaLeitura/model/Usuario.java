package br.com.yomu.gamificacaoDaLeitura.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "nome_usuario", nullable = false, unique = true)
    private String nomeUsuario;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @Column(name = "xp_total", nullable = false)
    private Integer xpTotal = 0;

    @Column(name = "nivel_atual", nullable = false)
    private Integer nivelAtual = 1;

    @Column(name = "codigo_convite", unique = true)
    private String codigoConvite;

    // Construtor útil para criar novos usuários
    public Usuario(String nomeUsuario, String email, String fotoPerfil) {
        this.id = UUID.randomUUID();
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.fotoPerfil = fotoPerfil;
        this.xpTotal = 0;
        this.nivelAtual = 1;
        this.codigoConvite = null;
    }
}
