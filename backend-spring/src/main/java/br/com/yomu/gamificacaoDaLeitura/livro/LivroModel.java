package br.com.yomu.gamificacaoDaLeitura.livro;

import br.com.yomu.gamificacaoDaLeitura.user.UserModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "Livros")
public class LivroModel {
    @Id
    private int id;

    private String titulo;
    private String autor;
    private String genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private UserModel dono;
    
    private int quantPaginas;
    private int pagAtual = 0;
}
