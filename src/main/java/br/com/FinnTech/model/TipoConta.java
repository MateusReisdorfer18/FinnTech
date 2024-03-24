package br.com.FinnTech.model;

public class TipoConta {
    private Integer id;
    private String nome;

    public TipoConta() {}

    public TipoConta(String nome) {
        this.nome = nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }
}
