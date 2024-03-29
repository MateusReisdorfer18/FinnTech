package br.com.FinnTech.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private List<Conta> contas;

    public Cliente() {
        this.contas = new ArrayList<>();
    }

    public Cliente(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.contas = new ArrayList<>();
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Conta> getContas() {
        return this.contas;
    }

    public void setContas(List<Conta> extrato) {
        this.contas = extrato;
    }

    public void vincularConta(Conta conta) {
        this.contas.add(conta);
    }

    public void desvincularConta(Conta conta) {
        this.contas.remove(conta);
    }
}
