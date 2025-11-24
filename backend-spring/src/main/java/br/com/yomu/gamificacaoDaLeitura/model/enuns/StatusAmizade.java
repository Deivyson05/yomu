package br.com.yomu.gamificacaoDaLeitura.model.enums;

public enum StatusAmizade {
    PENDENTE("Aguardando aceitação"),
    ACEITA("Amizade confirmada"),
    BLOQUEADA("Usuário bloqueado");

    private final String descricao;

    StatusAmizade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}