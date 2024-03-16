package br.com.FinnTech.model.contas;

import br.com.FinnTech.model.Cliente;
import br.com.FinnTech.model.Conta;
import br.com.FinnTech.model.Pagamento;

import java.util.ArrayList;

public class ContaCorrente extends Conta {
    public ContaCorrente(Integer id, Cliente cliente, Integer tipo, Double saldo, Double limite, String banco){
        this.setId(id);
        this.setCliente(cliente);
        this.setTipo(tipo);
        this.setSaldo(saldo);
        this.setLimite(limite);
        this.setBanco(banco);
        this.setExtrato(new ArrayList<Pagamento>());
    }
}
