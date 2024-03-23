package br.com.FinnTech.model;

public class Banco {
    private Integer id;
    private String nome;

    public Banco() {}

    public Banco(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
