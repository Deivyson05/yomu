package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Meta;
import br.com.yomu.gamificacaoDaLeitura.model.Progresso;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.model.enums.UnidadeMeta;
import br.com.yomu.gamificacaoDaLeitura.repository.MetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public Meta criar(UUID usuarioId, Meta meta) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        meta.setUsuario(usuario);
        meta.setDataInicio(LocalDateTime.now());
        
        // dataFim será calculada automaticamente pelo @PrePersist
        return metaRepository.save(meta);
    }

    public Meta buscarPorId(UUID id) {
        return metaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada"));
    }

    public List<Meta> listarPorUsuario(UUID usuarioId) {
        return metaRepository.findByUsuarioId(usuarioId);
    }

    public List<Meta> listarMetasAtivas(UUID usuarioId) {
        return metaRepository.findMetasAtivasUsuario(usuarioId, LocalDateTime.now());
    }

    public List<Meta> listarMetasConcluidas(UUID usuarioId) {
        return metaRepository.findByUsuarioIdAndConcluida(usuarioId, true);
    }

    @Transactional
    public void atualizarMetasComProgresso(UUID usuarioId, Progresso progresso) {
        List<Meta> metasAtivas = listarMetasAtivas(usuarioId);
        
        for (Meta meta : metasAtivas) {
            boolean deveAtualizar = false;
            
            switch (meta.getUnidadeMeta()) {
                case PAGINAS:
                    if (progresso.getTipoProgresso().name().equals("PAGINA")) {
                        meta.atualizarProgresso(progresso.getQuantidade());
                        deveAtualizar = true;
                    }
                    break;
                case CAPITULOS:
                    if (progresso.getTipoProgresso().name().equals("CAPITULO")) {
                        meta.atualizarProgresso(progresso.getQuantidade());
                        deveAtualizar = true;
                    }
                    break;
                case LIVROS:
                    // Será atualizado quando marcar livro como finalizado
                    break;
            }
            
            if (deveAtualizar) {
                metaRepository.save(meta);
            }
        }
    }

    @Transactional
    public void atualizarMetaLivroCompleto(UUID usuarioId) {
        List<Meta> metasAtivas = listarMetasAtivas(usuarioId);
        
        for (Meta meta : metasAtivas) {
            if (meta.getUnidadeMeta() == UnidadeMeta.LIVROS) {
                meta.atualizarProgresso(1);
                metaRepository.save(meta);
            }
        }
    }

    @Transactional
    public void deletar(UUID id) {
        metaRepository.deleteById(id);
    }
}
