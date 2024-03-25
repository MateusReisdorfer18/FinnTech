package br.com.FinnTech.model;

public class TipoPagamento {
    private Integer id;
    private String tipo;

    public TipoPagamento() {}
    public TipoPagamento(String tipo) {
        this.tipo = tipo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }
}
