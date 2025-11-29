package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Usuario;
import br.com.yomu.gamificacaoDaLeitura.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada aos usuários.
 * 
 * Contém métodos para criar, buscar, atualizar e deletar usuários,
 * além de funcionalidades específicas como adicionar XP e calcular níveis.
 */
@Service
public class UsuariosService {
    
    // Injeção automática do repositório para acesso aos dados
    @Autowired
    private UsuariosRepository usuariosRepository;
    
    /**
     * Cria um novo usuário no sistema.
     * 
     * Valida se o nome de usuário e email já não existem antes de salvar.
     * 
     * @param usuario Objeto com os dados do usuário a ser criado
     * @return O usuário criado com ID e timestamps gerados
     * @throws IllegalArgumentException se nome de usuário ou email já existirem
     */
    @Transactional
    public Usuario criarUsuario(Usuario usuario) {
        if (usuariosRepository.existsByNomeUsuario(usuario.getNomeUsuario())) {
            throw new IllegalArgumentException("Nome de usuário já existe");
        }
        
        if (usuariosRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        return usuariosRepository.save(usuario);
    }   
    
    /**
     * Retorna todos os usuários cadastrados no sistema.
     * 
     * @return Lista com todos os usuários
     */
    public List<Usuario> listarTodos() {
        return usuariosRepository.findAll();
    }
    
    /**
     * Busca um usuário específico pelo seu ID único.
     * 
     * @param id UUID do usuário
     * @return O usuário encontrado
     * @throws IllegalArgumentException se o usuário não existir
     */
    public Usuario buscarPorId(UUID id) {
        return usuariosRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
    
    /**
     * Busca um usuário pelo nome de usuário.
     * 
     * @param nomeUsuario Nome de usuário a ser buscado
     * @return O usuário encontrado
     * @throws IllegalArgumentException se o usuário não existir
     */
    public Usuario buscarPorNomeUsuario(String nomeUsuario) {
        return usuariosRepository.findByNomeUsuario(nomeUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
    
    /**
     * Busca um usuário através do código de convite.
     * 
     * Útil para funcionalidades de convite e indicação entre usuários.
     * 
     * @param codigoConvite Código único de convite
     * @return O usuário encontrado
     * @throws IllegalArgumentException se o código for inválido
     */
    public Usuario buscarPorCodigoConvite(String codigoConvite) {
        return usuariosRepository.findByCodigoConvite(codigoConvite)
            .orElseThrow(() -> new IllegalArgumentException("Código de convite inválido"));
    }
    
    /**
     * Atualiza os dados de um usuário existente.
     * 
     * Atualmente permite atualizar apenas nome de usuário e foto de perfil.
     * 
     * @param id ID do usuário a ser atualizado
     * @param usuarioAtualizado Objeto com os novos dados
     * @return O usuário atualizado
     * @throws IllegalArgumentException se o usuário não existir
     */
    @Transactional
    public Usuario atualizarUsuario(UUID id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);
        
        if (usuarioAtualizado.getNomeUsuario() != null) {
            usuario.setNomeUsuario(usuarioAtualizado.getNomeUsuario());
        }
        
        if (usuarioAtualizado.getFotoPerfil() != null) {
            usuario.setFotoPerfil(usuarioAtualizado.getFotoPerfil());
        }
        
        return usuariosRepository.save(usuario);
    }
    
    /**
     * Adiciona pontos de experiência (XP) ao usuário.
     * 
     * O sistema automaticamente recalcula o nível do usuário com base no XP total.
     * Regra atual: 100 XP = 1 nível (começa no nível 1)
     * 
     * @param id ID do usuário
     * @param xp Quantidade de XP a ser adicionada
     * @return O usuário com XP e nível atualizados
     * @throws IllegalArgumentException se o usuário não existir
     */
    @Transactional
    public Usuario adicionarXp(UUID id, int xp) {
        Usuario usuario = buscarPorId(id);
        usuario.setXpTotal(usuario.getXpTotal() + xp);
        
        // Recalcula nível (100 XP por nível)
        usuario.setNivelAtual((usuario.getXpTotal() / 100) + 1);
        
        return usuariosRepository.save(usuario);
    }
    
    /**
     * Remove um usuário do sistema.
     * 
     * @param id ID do usuário a ser deletado
     * @throws IllegalArgumentException se o usuário não existir
     */
    @Transactional
    public void deletarUsuario(UUID id) {
        if (!usuariosRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        usuariosRepository.deleteById(id);
    }
}