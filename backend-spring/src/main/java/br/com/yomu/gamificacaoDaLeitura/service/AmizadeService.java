package com.yomu.service;

import com.yomu.entity.Amizade;
import com.yomu.entity.Usuario;
import com.yomu.enums.StatusAmizade;
import com.yomu.repository.AmizadeRepository;
import com.yomu.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AmizadeService {
    
    @Autowired
    private AmizadeRepository amizadeRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Envia uma solicitação de amizade
     */
    @Transactional
    public Amizade solicitarAmizade(Integer usuarioId1, Integer usuarioId2, String mensagem) {
        // Validações
        if (usuarioId1.equals(usuarioId2)) {
            throw new IllegalArgumentException("Não é possível enviar solicitação de amizade para si mesmo");
        }
        
        // Verificar se os usuários existem
        if (!usuarioRepository.existsById(usuarioId1)) {
            throw new IllegalArgumentException("Usuário solicitante não encontrado");
        }
        if (!usuarioRepository.existsById(usuarioId2)) {
            throw new IllegalArgumentException("Usuário destinatário não encontrado");
        }
        
        // Verificar se já existe uma relação entre os usuários
        Optional<Amizade> amizadeExistente = amizadeRepository.findByUsuarios(usuarioId1, usuarioId2);
        if (amizadeExistente.isPresent()) {
            Amizade amizade = amizadeExistente.get();
            if (amizade.isPendente()) {
                throw new IllegalStateException("Já existe uma solicitação pendente entre esses usuários");
            } else if (amizade.isAceita()) {
                throw new IllegalStateException("Vocês já são amigos");
            } else if (amizade.isBloqueada()) {
                throw new IllegalStateException("Não é possível enviar solicitação para este usuário");
            }
        }
        
        // Criar nova amizade
        Amizade amizade = new Amizade(usuarioId1, usuarioId2);
        amizade.setMensagemSolicitacao(mensagem);
        
        return amizadeRepository.save(amizade);
    }
    
    /**
     * Aceita uma solicitação de amizade
     */
    @Transactional
    public Amizade aceitarAmizade(Integer amizadeId, Integer usuarioId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
            .orElseThrow(() -> new RuntimeException("Amizade não encontrada"));
        
        // Verificar se o usuário é o destinatário da solicitação
        if (!amizade.getUsuarioId2().equals(usuarioId)) {
            throw new IllegalStateException("Apenas o destinatário pode aceitar a solicitação");
        }
        
        // Verificar se está pendente
        if (!amizade.isPendente()) {
            throw new IllegalStateException("Esta solicitação não está pendente");
        }
        
        amizade.aceitar();
        return amizadeRepository.save(amizade);
    }
    
    /**
     * Recusa uma solicitação de amizade
     */
    @Transactional
    public void recusarAmizade(Integer amizadeId, Integer usuarioId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
            .orElseThrow(() -> new RuntimeException("Amizade não encontrada"));
        
        // Verificar se o usuário é o destinatário
        if (!amizade.getUsuarioId2().equals(usuarioId)) {
            throw new IllegalStateException("Apenas o destinatário pode recusar a solicitação");
        }
        
        amizade.recusar();
        amizadeRepository.save(amizade);
    }
    
    /**
     * Remove uma amizade (desfaz amizade)
     */
    @Transactional
    public void removerAmizade(Integer amizadeId, Integer usuarioId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
            .orElseThrow(() -> new RuntimeException("Amizade não encontrada"));
        
        // Verificar se o usuário faz parte da amizade
        if (!amizade.contemUsuario(usuarioId)) {
            throw new IllegalStateException("Você não faz parte desta amizade");
        }
        
        amizadeRepository.delete(amizade);
    }
    
    /**
     * Bloqueia um usuário
     */
    @Transactional
    public Amizade bloquearUsuario(Integer usuarioId1, Integer usuarioId2) {
        Optional<Amizade> amizadeOpt = amizadeRepository.findByUsuarios(usuarioId1, usuarioId2);
        
        Amizade amizade;
        if (amizadeOpt.isPresent()) {
            amizade = amizadeOpt.get();
        } else {
            amizade = new Amizade(usuarioId1, usuarioId2);
        }
        
        amizade.bloquear();
        return amizadeRepository.save(amizade);
    }
    
    /**
     * Marca/desmarca amigo como favorito
     */
    @Transactional
    public Amizade alternarFavorito(Integer amizadeId, Integer usuarioId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
            .orElseThrow(() -> new RuntimeException("Amizade não encontrada"));
        
        if (!amizade.contemUsuario(usuarioId)) {
            throw new IllegalStateException("Você não faz parte desta amizade");
        }
        
        if (!amizade.isAceita()) {
            throw new IllegalStateException("Só é possível favoritar amigos aceitos");
        }
        
        amizade.setEhFavorito(!amizade.getEhFavorito());
        return amizadeRepository.save(amizade);
    }
    
    /**
     * Alterna notificações de um amigo
     */
    @Transactional
    public Amizade alternarNotificacoes(Integer amizadeId, Integer usuarioId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
            .orElseThrow(() -> new RuntimeException("Amizade não encontrada"));
        
        if (!amizade.contemUsuario(usuarioId)) {
            throw new IllegalStateException("Você não faz parte desta amizade");
        }
        
        amizade.setNotificacoesAtivas(!amizade.getNotificacoesAtivas());
        return amizadeRepository.save(amizade);
    }
    
    /**
     * Lista todos os amigos aceitos de um usuário
     */
    public List<Usuario> listarAmigos(Integer usuarioId) {
        List<Amizade> amizades = amizadeRepository.findAmigosAceitos(usuarioId);
        
        return amizades.stream()
            .map(a -> {
                Integer amigoId = a.getOutroUsuarioId(usuarioId);
                return usuarioRepository.findById(amigoId).orElse(null);
            })
            .filter(u -> u != null)
            .collect(Collectors.toList());
    }
    
    /**
     * Lista solicitações de amizade recebidas (pendentes)
     */
    public List<Amizade> listarSolicitacoesRecebidas(Integer usuarioId) {
        return amizadeRepository.findSolicitacoesRecebidas(usuarioId);
    }
    
    /**
     * Lista solicitações de amizade enviadas (pendentes)
     */
    public List<Amizade> listarSolicitacoesEnviadas(Integer usuarioId) {
        return amizadeRepository.findSolicitacoesEnviadas(usuarioId);
    }
    
    /**
     * Lista amigos favoritos
     */
    public List<Usuario> listarAmigosFavoritos(Integer usuarioId) {
        List<Amizade> amizades = amizadeRepository.findAmigosFavoritos(usuarioId);
        
        return amizades.stream()
            .map(a -> {
                Integer amigoId = a.getOutroUsuarioId(usuarioId);
                return usuarioRepository.findById(amigoId).orElse(null);
            })
            .filter(u -> u != null)
            .collect(Collectors.toList());
    }
    
    /**
     * Verifica se dois usuários são amigos
     */
    public boolean saoAmigos(Integer usuario1, Integer usuario2) {
        return amizadeRepository.saoAmigos(usuario1, usuario2);
    }
    
    /**
     * Conta quantos amigos um usuário tem
     */
    public Long contarAmigos(Integer usuarioId) {
        return amizadeRepository.countAmigos(usuarioId);
    }
    
    /**
     * Busca uma amizade específica por ID
     */
    public Amizade buscarPorId(Integer id) {
        return amizadeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Amizade não encontrada"));
    }
}
