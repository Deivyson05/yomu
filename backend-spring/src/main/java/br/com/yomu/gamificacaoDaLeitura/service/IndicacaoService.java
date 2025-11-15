package com.yomu.service;

import com.yomu.model.Indicacao;
import com.yomu.model.Indicacao.StatusIndicacao;
import com.yomu.repository.IndicacaoRepository;
import com.yomu.exception.IndicacaoException;

import java.util.List;
import java.util.Optional;



public class IndicacaoService {
    
    private final IndicacaoRepository repository;
    
    public IndicacaoService(IndicacaoRepository repository) {
        this.repository = repository;
    }
    
    public Indicacao criar(String usuarioIndicadorId, String usuarioIndicadoId, 
                          String livroId, String mensagem) throws IndicacaoException {
        
        validarIndicacaoDuplicada(usuarioIndicadorId, usuarioIndicadoId, livroId);
        
        Indicacao indicacao = new Indicacao(usuarioIndicadorId, usuarioIndicadoId, 
                                           livroId, mensagem);
        return repository.save(indicacao);
    }
    
    public Optional<Indicacao> buscarPorId(String id) {
        return repository.findById(id);
    }
    
    public List<Indicacao> listarPorUsuario(String usuarioId) {
        return repository.findByUsuarioIndicadoId(usuarioId);
    }
    
    public List<Indicacao> listarPendentes(String usuarioId) {
        return repository.findByUsuarioIndicadoIdAndStatus(usuarioId, StatusIndicacao.PENDENTE);
    }
    
    public Indicacao aceitar(String indicacaoId, String usuarioId) throws IndicacaoException {
        Indicacao indicacao = buscarEValidar(indicacaoId, usuarioId);
        indicacao.aceitar();
        return repository.update(indicacao);
    }
    
    public Indicacao recusar(String indicacaoId, String usuarioId) throws IndicacaoException {
        Indicacao indicacao = buscarEValidar(indicacaoId, usuarioId);
        indicacao.recusar();
        return repository.update(indicacao);
    }
    
    public void excluir(String id) {
        repository.delete(id);
    }
    
    private void validarIndicacaoDuplicada(String indicadorId, String indicadoId, 
                                          String livroId) throws IndicacaoException {
        boolean existe = repository.findByUsuarioIndicadorId(indicadorId).stream()
            .anyMatch(i -> i.getUsuarioIndicadoId().equals(indicadoId) &&
                          i.getLivroId().equals(livroId) &&
                          i.isPendente());
        
        if (existe) {
            throw new IndicacaoException("Indicação duplicada");
        }
    }
    
    private Indicacao buscarEValidar(String indicacaoId, String usuarioId) 
            throws IndicacaoException {
        
        Indicacao indicacao = buscarPorId(indicacaoId)
            .orElseThrow(() -> new IndicacaoException("Indicação não encontrada"));
        
        if (!indicacao.getUsuarioIndicadoId().equals(usuarioId)) {
            throw new IndicacaoException("Sem permissão");
        }
        
        return indicacao;
    }
}