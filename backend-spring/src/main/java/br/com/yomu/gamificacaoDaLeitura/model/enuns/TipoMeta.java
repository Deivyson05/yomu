package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum TipoMeta {
    DIARIA("Meta Diária", 1),
    SEMANAL("Meta Semanal", 7),
    MENSAL("Meta Mensal", 30);

    private final String descricao;
    private final Integer duracaoDias;

    TipoMeta(String descricao, Integer duracaoDias) {
        this.descricao = descricao;
        this.duracaoDias = duracaoDias;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getDuracaoDias() {
        return duracaoDias;
    }
}