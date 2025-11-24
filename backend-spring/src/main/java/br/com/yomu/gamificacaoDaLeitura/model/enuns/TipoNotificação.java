package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum TipoNotificacao {
    AMIZADE_SOLICITADA("Solicitação de amizade recebida", "👋", "azul"),
    AMIZADE_ACEITA("Amizade aceita", "✅", "verde"),
    INDICACAO_RECEBIDA("Indicação de livro recebida", "📚", "roxo"),
    META_CONCLUIDA("Meta de leitura concluída", "🎯", "laranja"),
    NIVEL_ALCANCADO("Novo nível alcançado", "⭐", "dourado"),
    RANKING_ATUALIZADO("Posição no ranking atualizada", "🏆", "vermelho");

    private final String descricao;
    private final String icone;
    private final String cor;

    TipoNotificacao(String descricao, String icone, String cor) {
        this.descricao = descricao;
        this.icone = icone;
        this.cor = cor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIcone() {
        return icone;
    }

    public String getCor() {
        return cor;
    }
}