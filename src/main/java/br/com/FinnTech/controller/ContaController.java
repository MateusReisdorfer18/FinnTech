package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.ClienteDAOimpl;
import br.com.FinnTech.DAO.ContaDAOimpl;
import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContaController {
    public ContaController() {}

    public List<Conta> listarTodos() {
        try {
            GenericDAO dao = new ContaDAOimpl();
            List<Conta> contas = new ArrayList<>();

            for(Object objeto:dao.listarTodos()) {
                contas.add((Conta) objeto);
            }

            return contas;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao listar todas as contas");
            e.printStackTrace();
            return null;
        }
    }

    public Conta buscarPorId(Integer id) {
        try {
            GenericDAO dao = new ContaDAOimpl();
             return (Conta) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao buscar conta pelo id");
            e.printStackTrace();
            return null;
        }
    }

    public boolean cadastrar(Conta conta) {
        try {
            GenericDAO dao = new ContaDAOimpl();
            dao.criar(conta);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao cadastrar conta");
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Conta conta) {
        try {
            GenericDAO dao = new ContaDAOimpl();
            dao.alterar(conta);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao editar banco");
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Integer id) {
        try {
            GenericDAO dao = new ContaDAOimpl();
            dao.excluir(id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao excluir conta");
            e.printStackTrace();
            return false;
        }
    }

    public boolean depositar(Double valor, Integer id) {
        try {
            ContaDAOimpl dao = new ContaDAOimpl();
            dao.depositar(valor, id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao efetuar deposito");
            e.printStackTrace();
            return false;
        }
    }

    public boolean sacar (Double valor, Integer id) {
        try {
            ContaDAOimpl dao = new ContaDAOimpl();
            dao.sacar(valor, id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao efetuar saque");
            e.printStackTrace();
            return false;
        }
    }

    public Conta buscarPorNumero(Integer numero){
        Conta conta = new Conta();

        try {
            ContaDAOimpl dao = new ContaDAOimpl();
            conta = dao.buscarPorNumero(numero);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao buscar conta pelo numero");
            e.printStackTrace();
        }

        return conta;
    }

    private List<Pagamento> montarExtrato(Integer id) {
        List<Pagamento> extrato = new ArrayList<>();

        try {
            ContaDAOimpl dao = new ContaDAOimpl();

            extrato.addAll(dao.verExtrato(id));
        } catch (Exception e) {
            System.out.println("Problemas no controller ao carregar extrato da conta");
            e.printStackTrace();
        }

        return extrato;
    }

    public void verExtrato(Conta conta) {
        for(Pagamento pagamento:this.montarExtrato(conta.getId())) {
            Integer id = pagamento.getId();
            String tipo = pagamento.getTipo().getTipo();
            String nomeClienteRemetente = pagamento.getRemetente().getCliente().getNome();
            Integer numeroContaRemetente = pagamento.getRemetente().getNumero();
            String nomeClienteDestinatario = pagamento.getDestinatario().getCliente().getNome();
            Integer numeroContaDestinatario = pagamento.getDestinatario().getNumero();
            Double valor = pagamento.getValor();

            System.out.printf("""
                    Pagamento     \s
                        Id: %d \s
                        Tipo: %s \s
                        Remetente: \s
                            Nome: %s \s
                            Numero: %d \s
                        Destinatario : \s
                            Nome: %s \s
                            Numero: %d \s
                        Valor: %f   \s
                    """, id, tipo, nomeClienteRemetente, numeroContaRemetente, nomeClienteDestinatario, numeroContaDestinatario, valor);
        }

    }

    protected void menuVerSaldo(Conta conta) {
        System.out.printf("Saldo: R$%f", conta.getSaldo());
    }

    protected boolean menuCadastrar(Scanner scan, ClienteController clienteController, BancoController bancoController, TipoContaController tipoContaController) {
        int idCliente;
        Cliente cliente;
        int idTipo;
        TipoConta tipoConta;
        int idBanco;
        Banco banco;


        do {
            clienteController.menuListarTodos();

            System.out.println("Digite o id do Cliente da conta");
            idCliente = scan.nextInt();

            cliente = clienteController.buscarPorId(idCliente);

            if(cliente == null)
                System.out.printf("Cliente não encontrado com o id %d, digite novamente \n", idCliente);
        } while(cliente == null);

        do {
            tipoContaController.mostrarTipos();

            System.out.println("Digite o id do tipo da conta");
            idTipo = scan.nextInt();

            tipoConta = tipoContaController.buscarPorId(idTipo);

            if(tipoConta == null)
                System.out.printf("Tipo conta não encontrada com o id %d, digite novamente \n", idTipo);
        } while (tipoConta == null);

        do {
            System.out.println("""
                        Selecione o Banco \s
                        [1] Caixa \s
                        [2] Itaú \s
                        [3] Bradesco \s
                        [4] Santander \s
                        [5] Banco do Brasil
                    """);
            idBanco = scan.nextInt();

            banco = bancoController.buscarPorId(idBanco);

            if(banco == null)
                System.out.printf("Banco não encontrado com o id %d, digite novamente \n", idBanco);
        } while(banco == null);

        Conta conta = new Conta(cliente, tipoConta, 0.00, 10000.00, banco);

        return this.cadastrar(conta);
    }

    protected void menuListarTodos() {
        for(Conta conta:this.listarTodos()) {
            System.out.printf("""
                        Id: %d \s
                        Numero: %d \s
                        Tipo: %s \s
                        Cliente: %s \s
                        Saldo: %f \s
                        Limite: %f \s
                        Banco: %s
                    """, conta.getId(), conta.getNumero(), conta.getTipo().getNome(), conta.getCliente().getNome(), conta.getSaldo(), conta.getLimite(), conta.getBanco().getNome());
        }
    }

    protected boolean menuContaTransferir(Scanner scan, Conta remetente, Conta destinatario) {
        boolean returnValidarValorPagamento;
        boolean returnSaque;
        boolean returnDeposito;
        Double valor;

        System.out.println("Digite o valor do pagamento");
        valor = scan.nextDouble();

        returnValidarValorPagamento = this.validarValorPagamento(remetente, valor);

        if(!returnValidarValorPagamento) {
            if(valor <= 0)
                System.out.println("Valor do pagamento tem que ser maior que R$0,00");
            else
                System.out.println("Saldo insufisciente para efetuar o pagamento");

            return false;
        }

        returnSaque = this.sacar(valor, remetente.getId());
        returnDeposito = this.depositar(valor, destinatario.getId());

        if(!returnSaque || !returnDeposito) {
            return false;
        }

        return true;
    }

    protected boolean menuAlterar(Scanner scan, TipoContaController tipoContaController) {
        int idConta;
        Conta conta;
        int idTipo;
        TipoConta tipoConta;

        do {
            this.menuListarTodos();

            System.out.println("Digite o id da conta");
            idConta = scan.nextInt();

            conta = this.buscarPorId(idConta);

            if(conta == null)
                System.out.printf("Conta não encontrada com o id %d, digite novamente \n", idConta);
        } while(conta == null);

        do {
            tipoContaController.mostrarTipos();

            System.out.println("Digite o id do tipo");
            idTipo = scan.nextInt();

            tipoConta = tipoContaController.buscarPorId(idTipo);

            if(tipoConta == null)
                System.out.printf("Tipo Conta não encontrada com o id %d, digite novamente \n", idTipo);
        } while(tipoConta == null);

        conta.setTipo(tipoConta);

        return this.alterar(conta);
    }

    protected boolean menuContaDeposito(Scanner scan, Conta conta) {
        boolean returnDeposito;
        Double valor;

        do {
            System.out.println("Digite o valor do deposito");
            valor = scan.nextDouble();

            if(valor <= 0)
                System.out.println("O valor do pagamento tem que ser maior que R$0,00");
        } while (valor <= 0);

        returnDeposito = this.depositar(valor, conta.getId());

        if(!returnDeposito)
            return false;

        return true;
    }

    protected boolean menuContaSaque(Scanner scan, Conta conta) {
        boolean returnSaque;
        Double valor;

        do {
            System.out.println("Digite o valor do saque");
            valor = scan.nextDouble();

            if(valor <= 0)
                System.out.println("O valor do saque tem que ser maior que R$0,00");
        } while (valor <= 0);

        boolean returnValidarValorSaque = this.validarValorSaque(conta, valor);

        if(!returnValidarValorSaque) {
            System.out.println("O valor do saque é inválido, tente novamente");
            return false;
        }

        returnSaque = this.sacar(valor, conta.getId());

        if(!returnSaque)
            return false;

        return true;
    }

    protected boolean validarValorPagamento(Conta conta, Double valor) {
        return valor > 0 && valor < conta.getSaldo() && valor < conta.getLimite();
    }

    private boolean validarValorSaque(Conta conta, Double valor) {
        return valor < conta.getSaldo() && valor < conta.getLimite();
    }
}
