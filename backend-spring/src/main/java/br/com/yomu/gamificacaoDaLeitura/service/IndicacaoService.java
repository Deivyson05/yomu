package br.com.yomu.gamificacaoDaLeitura.service;

import br.com.yomu.gamificacaoDaLeitura.model.Indicacao;
import br.com.yomu.gamificacaoDaLeitura.model.StatusIndicacao;
import br.com.yomu.gamificacaoDaLeitura.repository.IndicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndicacaoService {

    private final IndicacaoRepository repository;

    public Indicacao criarIndicacao(UUID usuarioIndicadorId,
                                    UUID usuarioIndicadoId,
                                    String livroId,
                                    String mensagem) {

        Indicacao i = Indicacao.builder()
                .usuarioIndicadorId(usuarioIndicadorId)
                .usuarioIndicadoId(usuarioIndicadoId)
                .livroId(livroId)
                .mensagem(mensagem)
                .status(StatusIndicacao.PENDENTE)
                .build();

        return repository.save(i);
    }

    public List<Indicacao> listarRecebidas(UUID usuarioId) {
        return repository.findByUsuarioIndicadoId(usuarioId);
    }

    public List<Indicacao> listarEnviadas(UUID usuarioId) {
        return repository.findByUsuarioIndicadorId(usuarioId);
    }

    public Indicacao alterarStatus(UUID indicacaoId, StatusIndicacao status) {
        Indicacao i = repository.findById(indicacaoId).orElseThrow();
        i.setStatus(status);
        return repository.save(i);
    }
}
