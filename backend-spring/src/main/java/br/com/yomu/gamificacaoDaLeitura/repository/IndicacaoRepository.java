package com.yomu.repository;

import com.yomu.model.Indicacao;
import com.yomu.model.Indicacao.StatusIndicacao;

import java.util.List;
import java.util.Optional;




public interface IndicacaoRepository {
    
    Indicacao save(Indicacao indicacao);
    
    Indicacao update(Indicacao indicacao);
    
    Optional<Indicacao> findById(String id);
    
    List<Indicacao> findByUsuarioIndicadorId(String usuarioId);
    
    List<Indicacao> findByUsuarioIndicadoId(String usuarioId);
    
    List<Indicacao> findByUsuarioIndicadoIdAndStatus(String usuarioId, StatusIndicacao status);
    
    void delete(String id);
    
    List<Indicacao> findAll();
}