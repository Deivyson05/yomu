package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Livro;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoRegistro;
import br.com.yomu.gamificacaoDaLeitura.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public Livro criar(UUID usuarioId, Livro livro) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        livro.setUsuario(usuario);

        validarLivro(livro);
        return livroRepository.save(livro);
    }

    public Livro buscarPorId(UUID id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    }

    public List<Livro> listarPorUsuario(UUID usuarioId) {
        return livroRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId);
    }

    public List<Livro> listarPorUsuarioEStatus(UUID usuarioId, Boolean finalizado) {
        return livroRepository.findByUsuarioIdAndFinalizado(usuarioId, finalizado);
    }

    @Transactional
    public Livro atualizar(UUID id, Livro livroAtualizado) {
        Livro livro = buscarPorId(id);

        if (livroAtualizado.getTitulo() != null) livro.setTitulo(livroAtualizado.getTitulo());
        if (livroAtualizado.getAutor() != null) livro.setAutor(livroAtualizado.getAutor());
        if (livroAtualizado.getDescricao() != null) livro.setDescricao(livroAtualizado.getDescricao());
        if (livroAtualizado.getCapa() != null) livro.setCapa(livroAtualizado.getCapa());
        if (livroAtualizado.getFinalizado() != null) livro.setFinalizado(livroAtualizado.getFinalizado());

        return livroRepository.save(livro);
    }

    @Transactional
    public void marcarComoFinalizado(UUID id) {
        Livro livro = buscarPorId(id);
        livro.setFinalizado(true);
        livroRepository.save(livro);
    }

    @Transactional
    public void deletar(UUID id) {
        livroRepository.deleteById(id);
    }

    private void validarLivro(Livro livro) {
        if (livro.getTipoRegistro() == TipoRegistro.PAGINA &&
            (livro.getNumeroPaginas() == null || livro.getNumeroPaginas() <= 0)) {
            throw new IllegalArgumentException("Número de páginas deve ser maior que zero");
        }

        if (livro.getTipoRegistro() == TipoRegistro.CAPITULO &&
            (livro.getNumeroCapitulos() == null || livro.getNumeroCapitulos() <= 0)) {
            throw new IllegalArgumentException("Número de capítulos deve ser maior que zero");
        }
    }
}
