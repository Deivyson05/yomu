package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Indicacao;
import br.com.yomu.gamificacaoDaLeitura.model.Livro;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoNotificacao;
import br.com.yomu.gamificacaoDaLeitura.repository.IndicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndicacaoService {

    private final IndicacaoRepository indicacaoRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final NotificacaoService notificacaoService;

    @Transactional
    public Indicacao criar(UUID remetenteId, UUID destinatarioId, UUID livroId, String mensagem) {
        Usuario remetente = usuarioService.buscarPorId(remetenteId);
        Usuario destinatario = usuarioService.buscarPorId(destinatarioId);
        Livro livro = livroService.buscarPorId(livroId);
        
        if (remetenteId.equals(destinatarioId)) {
            throw new IllegalArgumentException("Não é possível indicar livros para si mesmo");
        }
        
        Indicacao indicacao = new Indicacao();
        indicacao.setRemetente(remetente);
        indicacao.setDestinatario(destinatario);
        indicacao.setLivro(livro);
        indicacao.setMensagem(mensagem);
        
        Indicacao indicacaoSalva = indicacaoRepository.save(indicacao);
        
        // Criar notificação para o destinatário
        notificacaoService.criar(
            destinatarioId,
            TipoNotificacao.INDICACAO_RECEBIDA,
            "Nova indicação de livro",
            remetente.getNomeUsuario() + " indicou o livro \"" + livro.getTitulo() + "\" para você!",
            "/indicacoes/" + indicacaoSalva.getId()
        );
        
        return indicacaoSalva;
    }

    public Indicacao buscarPorId(UUID id) {
        return indicacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Indicação não encontrada"));
    }

    public List<Indicacao> listarPorDestinatario(UUID destinatarioId) {
        return indicacaoRepository.findByDestinatarioIdOrderByCreatedAtDesc(destinatarioId);
    }

    public List<Indicacao> listarNaoLidas(UUID destinatarioId) {
        return indicacaoRepository.findByDestinatarioIdAndLida(destinatarioId, false);
    }

    public List<Indicacao> listarPorRemetente(UUID remetenteId) {
        return indicacaoRepository.findByRemetenteId(remetenteId);
    }

    public long contarNaoLidas(UUID destinatarioId) {
        return indicacaoRepository.countByDestinatarioIdAndLida(destinatarioId, false);
    }

    @Transactional
    public void marcarComoLida(UUID indicacaoId) {
        Indicacao indicacao = buscarPorId(indicacaoId);
        indicacao.setLida(true);
        indicacaoRepository.save(indicacao);
    }

    @Transactional
    public void marcarTodasComoLidas(UUID destinatarioId) {
        List<Indicacao> indicacoes = listarNaoLidas(destinatarioId);
        indicacoes.forEach(i -> i.setLida(true));
        indicacaoRepository.saveAll(indicacoes);
    }

    @Transactional
    public void deletar(UUID indicacaoId) {
        indicacaoRepository.deleteById(indicacaoId);
    }
}