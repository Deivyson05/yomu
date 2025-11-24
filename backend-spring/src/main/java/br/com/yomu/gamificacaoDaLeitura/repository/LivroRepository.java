package br.com.yomu.gamificacaoDaLeitura.repository;

import br.com.yomu.gamificacaoDaLeitura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    
    List<Livro> findByUsuarioId(UUID usuarioId);
    
    List<Livro> findByUsuarioIdAndFinalizado(UUID usuarioId, Boolean finalizado);
    
    List<Livro> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
}