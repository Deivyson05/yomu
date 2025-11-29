package br.com.yomu.gamificacaoDaLeitura.controller;

import br.com.yomu.gamificacaoDaLeitura.model.Indicacao;
import br.com.yomu.gamificacaoDaLeitura.model.StatusIndicacao;
import br.com.yomu.gamificacaoDaLeitura.service.IndicacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/indicacoes")
@RequiredArgsConstructor
public class IndicacaoController {

    private final IndicacaoService service;

    @PostMapping
    public Indicacao criarIndicacao(@RequestParam UUID usuarioIndicadorId,
                                    @RequestParam UUID usuarioIndicadoId,
                                    @RequestParam String livroId,
                                    @RequestParam String mensagem) {

        return service.criarIndicacao(usuarioIndicadorId, usuarioIndicadoId, livroId, mensagem);
    }

    @GetMapping("/recebidas/{usuarioId}")
    public List<Indicacao> recebidas(@PathVariable UUID usuarioId) {
        return service.listarRecebidas(usuarioId);
    }

    @GetMapping("/enviadas/{usuarioId}")
    public List<Indicacao> enviadas(@PathVariable UUID usuarioId) {
        return service.listarEnviadas(usuarioId);
    }

    @PatchMapping("/{id}/status")
    public Indicacao alterarStatus(@PathVariable UUID id,
                                   @RequestParam StatusIndicacao status) {
        return service.alterarStatus(id, status);
    }
}
