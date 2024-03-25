package br.com.FinnTech.model;

public class Pagamento {
    private Integer id;
    private TipoPagamento tipo;
    private Conta remetente;
    private Conta destinatario;
    private Double valor;

    public Pagamento() {
    }

    public Pagamento(TipoPagamento tipo, Conta remetente, Conta destinatario, Double valor) {
        this.tipo = tipo;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.valor = valor;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoPagamento getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }

    public Conta getRemetente() {
        return this.remetente;
    }

    public void setRemetente(Conta remetente) {
        this.remetente = remetente;
    }

    public Conta getDestinatario() {
        return this.destinatario;
    }

    public void setDestinatario(Conta destinatario) {
        this.destinatario = destinatario;
    }

    public Double getValor() {
        return this.valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
