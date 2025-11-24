package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Notificacao;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.model.enums.TipoNotificacao;
import br.com.yomu.gamificacaoDaLeitura.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public Notificacao criar(UUID usuarioId, TipoNotificacao tipo, String titulo, String mensagem, String link) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        
        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setTipoNotificacao(tipo);
        notificacao.setTitulo(titulo);
        notificacao.setMensagem(mensagem);
        notificacao.setLink(link);
        
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarPorUsuario(UUID usuarioId) {
        return notificacaoRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId);
    }

    public List<Notificacao> listarNaoLidas(UUID usuarioId) {
        return notificacaoRepository.findByUsuarioIdAndLida(usuarioId, false);
    }

    public long contarNaoLidas(UUID usuarioId) {
        return notificacaoRepository.countByUsuarioIdAndLida(usuarioId, false);
    }

    @Transactional
    public void marcarComoLida(UUID notificacaoId) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
            .orElseThrow(() -> new IllegalArgumentException("Notificação não encontrada"));
        
        notificacao.setLida(true);
        notificacaoRepository.save(notificacao);
    }

    @Transactional
    public void marcarTodasComoLidas(UUID usuarioId) {
        List<Notificacao> notificacoes = listarNaoLidas(usuarioId);
        notificacoes.forEach(n -> n.setLida(true));
        notificacaoRepository.saveAll(notificacoes);
    }

    @Transactional
    public void deletar(UUID notificacaoId) {
        notificacaoRepository.deleteById(notificacaoId);
    }
}