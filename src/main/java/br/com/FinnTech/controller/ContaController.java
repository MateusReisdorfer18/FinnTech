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

    public void chamarMenu(Scanner scan, ClienteController clienteController, BancoController bancoController, TipoContaController tipoContaController) {
        int opcao;

        System.out.println("""
                    Conta menu
                    Selecione uma opção
                    [1] Criar Conta \s
                    [2] Listar todas as Contas \s
                    [3] Buscar conta pelo id \s
                    [4] Editar Conta \s
                    [5] Excluir Conta \s
                """);
        opcao = scan.nextInt();

        switch (opcao) {
            case 1:
                boolean returnCadastro = this.menuCadastrar(scan, clienteController, bancoController, tipoContaController);

                if(!returnCadastro) {
                    System.out.println("Houve um problema ao criar conta");
                    break;
                }

                System.out.println("Conta criado com sucesso");

                break;
            case 2:
                this.menuListarTodos();

                break;
            case 3:
                this.menuBuscarPorId(scan);

                break;
            case 4:
                boolean returnAlterar = this.menuAlterar(scan, tipoContaController);

                if(!returnAlterar) {
                    System.out.println("Houve um problema ao editar conta");
                    break;
                }

                System.out.println("Conta editada com sucesso");

                break;
            case 5:
                boolean returnExcluir = this.menuExcluir(scan);

                if(!returnExcluir) {
                    System.out.println("Houve um problema ao excluir conta");
                    break;
                }

                System.out.println("Conta excluida");

                break;
            default:
                break;
        }
    }

    private boolean menuCadastrar(Scanner scan, ClienteController clienteController, BancoController bancoController, TipoContaController tipoContaController) {
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

        Conta conta = new Conta(cliente, tipoConta, 0.00, 0.00, banco);

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
    private void menuBuscarPorId(Scanner scan) {
        int idConta;
        Conta conta;

        do {
            this.menuListarTodos();

            System.out.println("Digite o id da conta");
            idConta = scan.nextInt();

            conta = this.buscarPorId(idConta);

            if(conta == null)
                System.out.printf("Conta não encontrada com o id %d, digite novamente \n", idConta);
        } while (conta == null);

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
    private boolean menuAlterar(Scanner scan, TipoContaController tipoContaController) {
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
    private boolean menuExcluir(Scanner scan) {
        int idConta;

        this.menuListarTodos();
        System.out.println("Digite o id da conta que deseja excluir");
        idConta = scan.nextInt();

        return this.excluir(idConta);
    }
}
