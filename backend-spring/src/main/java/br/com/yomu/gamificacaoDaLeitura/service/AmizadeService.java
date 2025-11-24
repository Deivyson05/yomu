package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Amizade;
import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.model.enums.StatusAmizade;
import br.com.yomu.gamificacaoDaLeitura.repository.AmizadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmizadeService {

    private final AmizadeRepository amizadeRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public Amizade enviarSolicitacao(UUID usuarioId1, UUID usuarioId2) {
        Usuario usuario1 = usuarioService.buscarPorId(usuarioId1);
        Usuario usuario2 = usuarioService.buscarPorId(usuarioId2);
        
        // Verifica se já existe uma amizade
        if (amizadeRepository.findAmizadeEntreUsuarios(usuarioId1, usuarioId2).isPresent()) {
            throw new IllegalArgumentException("Já existe uma relação entre esses usuários");
        }
        
        Amizade amizade = new Amizade();
        amizade.setUsuarioId1(usuario1);
        amizade.setUsuarioId2(usuario2);
        amizade.setStatus(StatusAmizade.PENDENTE);
        
        return amizadeRepository.save(amizade);
    }

    @Transactional
    public Amizade aceitarSolicitacao(UUID amizadeId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
            .orElseThrow(() -> new IllegalArgumentException("Amizade não encontrada"));
        
        if (amizade.getStatus() != StatusAmizade.PENDENTE) {
            throw new IllegalArgumentException("Esta solicitação não está pendente");
        }
        
        amizade.setStatus(StatusAmizade.ACEITA);
        amizade.setDataAceite(LocalDateTime.now());
        
        return amizadeRepository.save(amizade);
    }

    @Transactional
    public void bloquearUsuario(UUID amizadeId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
            .orElseThrow(() -> new IllegalArgumentException("Amizade não encontrada"));
        
        amizade.setStatus(StatusAmizade.BLOQUEADA);
        amizadeRepository.save(amizade);
    }

    public List<Amizade> listarSolicitacoesPendentes(UUID usuarioId) {
        return amizadeRepository.findSolicitacoesPendentes(usuarioId);
    }

    public List<Amizade> listarAmigos(UUID usuarioId) {
        return amizadeRepository.findAmizadesByUsuarioAndStatus(usuarioId, StatusAmizade.ACEITA);
    }

    @Transactional
    public void removerAmizade(UUID amizadeId) {
        amizadeRepository.deleteById(amizadeId);
    }
}