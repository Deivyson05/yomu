package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum TipoProgresso {
    PAGINA("Páginas"),
    CAPITULO("Capítulos");

    private final String descricao;

    TipoProgresso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
