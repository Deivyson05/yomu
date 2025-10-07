package br.com.yomu.gamificacaoDaLeitura.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "Usuarios")
public class UserModel {
    @Id
    private String email;

    private String nome;
    private LocalDate data_nascimento;
    private String genero;
    private String telefone;
    private String senha;

    private String userToken = UUID.randomUUID().toString();
    

    @CreationTimestamp
    private LocalDateTime createdAt;
}
