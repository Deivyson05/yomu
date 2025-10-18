package br.com.yomu.gamificacaoDaLeitura.model.enuns; 
import lombook.Getter;

@Getter

public enun TipoProgresso {

    PAGINA(1L, "PAGINA", "Páginas", 10);
    CAPITULO(2L, "CAPITULO", "Capítulos", 50);
    private final Long id;
    private final String codigo;
    private final String descricao;
    private final int xpPorUnidade;

    TipoProgresso(Long id, String codigo, String descricao, int xpPorUnidade) {

        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.xpPorUnidade = xpPorUnidade;
    }
    
    public static TipoProgresso fromId(Long id) {
        for (TipoProgresso tipo: value()) {
            if (tipo.getId().equals(id)) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("Tipo de progresso inválido: " + id);
    }

    public static TipoProgresso fromCodigo(String codigo) {

        for (TipoProgresso tipo : value()) {
            if (tipo.getCodigo().equalsIgnoreCase(codigo)) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("Código de tipo de progresso inválido: " + codigo);
    }

    public static int calcularXp (int quantidade) {
        return this.xpPorUnidade * quantidade;
    }
}