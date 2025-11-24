package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum TipoRegistro {
    PAGINA("Páginas"),
    CAPITULO("Capítulos");

    private final String descricao;

    TipoRegistro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}