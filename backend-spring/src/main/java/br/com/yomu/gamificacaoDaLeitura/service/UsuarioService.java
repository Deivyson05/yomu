package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // UsuariosService.java
    @Transactional(readOnly = true)
    public Usuario login(String email, String senha) {
        var usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));

        boolean ok = senha.equals(usuario.getSenha()); // <- simples, sem hash

        if (!ok) throw new IllegalArgumentException("Email ou senha inválidos");
        return usuario;
    }

    @Transactional
    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (usuarioRepository.existsByNomeUsuario(usuario.getNomeUsuario())) {
            throw new IllegalArgumentException("Nome de usuário já existe");
        }
        
        // Gerar código de convite único
        usuario.setCodigoConvite(gerarCodigoConvite());
        
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public Usuario buscarPorCodigoConvite(String codigoConvite) {
        return usuarioRepository.findByCodigoConvite(codigoConvite)
            .orElseThrow(() -> new IllegalArgumentException("Código de convite inválido"));
    }

    @Transactional
    public Usuario atualizar(UUID id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);
        
        if (usuarioAtualizado.getNomeUsuario() != null) {
            usuario.setNomeUsuario(usuarioAtualizado.getNomeUsuario());
        }
        if (usuarioAtualizado.getFotoPerfil() != null) {
            usuario.setFotoPerfil(usuarioAtualizado.getFotoPerfil());
        }
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void adicionarXp(UUID usuarioId, Long xp) {
        Usuario usuario = buscarPorId(usuarioId);
        usuario.setXpTotal(usuario.getXpTotal() + xp);
        
        // Calcular novo nível (exemplo: a cada 1000 XP = 1 nível)
        Integer novoNivel = (int) (usuario.getXpTotal() / 1000) + 1;
        usuario.setNivelAtual(novoNivel);
        
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public void deletar(UUID id) {
        usuarioRepository.deleteById(id);
    }

    private String gerarCodigoConvite() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}