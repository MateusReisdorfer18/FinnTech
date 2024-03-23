package br.com.FinnTech.model;

import java.util.ArrayList;
import java.util.List;

public class Conta {
    private Integer id;
    private Integer numero;
    private Cliente cliente;
    private Integer tipo;
    private Double saldo;
    private Double limite;
    private Banco banco;
    private List<Pagamento> extrato;

    public Conta() {
        this.extrato = new ArrayList<>();
    }

    public Conta(Integer numero, Cliente cliente, Integer tipo, Double saldo, Double limite, Banco banco) {
        this.numero = numero;
        this.cliente = cliente;
        this.tipo = tipo;
        this.saldo = saldo;
        this.limite = limite;
        this.banco = banco;
        this.extrato = new ArrayList<>();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getTipo() {
        return this.tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getLimite() {
        return this.limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Pagamento> getExtrato() {
        return this.extrato;
    }

    public void setExtrato(List<Pagamento> extrato) {
        this.extrato = extrato;
    }
}
