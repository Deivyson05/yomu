package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum UnidadeMeta {
    PAGINAS("Páginas", "📄"),
    CAPITULOS("Capítulos", "📖"),
    LIVROS("Livros Completos", "📚");

    private final String descricao;
    private final String icone;

    UnidadeMeta(String descricao, String icone) {
        this.descricao = descricao;
        this.icone = icone;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIcone() {
        return icone;
    }
}