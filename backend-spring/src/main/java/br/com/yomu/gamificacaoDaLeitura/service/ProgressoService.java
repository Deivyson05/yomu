package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Livro;
import br.com.yomu.gamificacaoDaLeitura.model.Progresso;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.repository.ProgressoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;
    private final MetaService metaService;

    @Transactional
    public Progresso registrar(UUID usuarioId, UUID livroId, Progresso progresso) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Livro livro = livroService.buscarPorId(livroId);
        
        progresso.setUsuario(usuario);
        progresso.setLivro(livro);
        
        // XP será calculado automaticamente pelo @PrePersist
        Progresso progressoSalvo = progressoRepository.save(progresso);
        
        // Adicionar XP ao usuário
        usuarioService.adicionarXp(usuarioId, progressoSalvo.getXpGerado());
        
        // Atualizar metas ativas
        metaService.atualizarMetasComProgresso(usuarioId, progresso);
        
        return progressoSalvo;
    }

    public List<Progresso> listarPorUsuario(UUID usuarioId) {
        return progressoRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId);
    }

    public List<Progresso> listarPorLivro(UUID livroId) {
        return progressoRepository.findByLivroId(livroId);
    }

    public List<Progresso> listarPorPeriodo(UUID usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        return progressoRepository.findByUsuarioIdAndPeriodo(usuarioId, inicio, fim);
    }

    public Long calcularXpTotal(UUID usuarioId) {
        Long xp = progressoRepository.calcularXpTotalUsuario(usuarioId);
        return xp != null ? xp : 0L;
    }
}