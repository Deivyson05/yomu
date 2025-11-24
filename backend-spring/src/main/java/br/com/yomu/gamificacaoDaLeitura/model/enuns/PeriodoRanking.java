package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum PeriodoRanking {
    SEMANAL("Esta Semana", 7),
    MENSAL("Este Mês", 30),
    ANUAL("Este Ano", 365),
    TOTAL("Todo o Período", null);

    private final String descricao;
    private final Integer dias;

    PeriodoRanking(String descricao, Integer dias) {
        this.descricao = descricao;
        this.dias = dias;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getDias() {
        return dias;
    }
}