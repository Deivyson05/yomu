package br.com.yomu.gamificacaoDaLeitura.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity(name = "Usuarios")
public class UserModel {
    @Id
    private String email;

    private String username;
    private String nome;
    private LocalDate data_nascimento;
    private String genero;
    private String telefone;
    private String senha;

    private int xpTotal = 0;
    private int xpSemanal = 0;
    private int divisao = 0;
    private ArrayList<String> amigos = new ArrayList<>(Arrays.asList("Filomena123", "Vovozinha", "Feia"));
    private int lidas = 0;

    private String userToken = UUID.randomUUID().toString();
    

    @CreationTimestamp
    private LocalDateTime createdAt;
}
