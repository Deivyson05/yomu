package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum TipoRanking {
    GERAL("Ranking Geral"),
    AMIGOS("Ranking de Amigos");

    private final String descricao;

    TipoRanking(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}