package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.ClienteDAOimpl;
import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.model.Cliente;
import br.com.FinnTech.model.Conta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ClienteController {
    public ClienteController() {}

    private List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<Cliente>();

        try {
            GenericDAO dao = new ClienteDAOimpl();

            for(Object objeto:dao.listarTodos()) {
                clientes.add((Cliente) objeto);
            }

        } catch (Exception e) {
            System.out.println("Problema no Controller ao listar todos");
            e.printStackTrace();
        }

        return clientes;
    }

    public Cliente buscarPorId(Integer id) {
        Cliente cliente;

        try {
            GenericDAO dao = new ClienteDAOimpl();
            cliente = (Cliente) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao ");
            e.printStackTrace();
            return null;
        }

        return cliente;
    }

    private boolean criar(Cliente cliente) {
        try {
            GenericDAO dao = new ClienteDAOimpl();
            dao.criar(cliente);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao cadastrar cliente");
            e.printStackTrace();
            return false;
        }
    }

    private boolean alterar(Cliente cliente) {
        try {
            GenericDAO dao = new ClienteDAOimpl();
            dao.alterar(cliente);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao editar cliente");
            e.printStackTrace();
            return false;
        }
    }

    private boolean excluir(Integer id) {
        try {
            GenericDAO dao = new ClienteDAOimpl();
            dao.excluir(id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao excluir cliente");
            e.printStackTrace();
            return false;
        }
    }

    private List<Conta> listarContas(Integer id) {
        List<Conta> contas = new ArrayList<>();

        try {
            ClienteDAOimpl dao = new ClienteDAOimpl();
            for(Object objeto:dao.listarContas(id)) {
                contas.add((Conta) objeto);
            }
        } catch (Exception e) {
            System.out.println("Problemas no controller ao listar contas do cliente");
            e.printStackTrace();
        }

        return contas;
    }

    public void chamarMenu(Scanner scan, ContaController contaController) {
        int opcao;

        System.out.println("""
                    Cliente Menu \s
                    [1] Cadastrar Cliente \s
                    [2] Listar Clientes \s
                    [3] Buscar Cliente pelo id \s
                    [4] Editar Cliente \s
                    [5] Excluir Cliente \s
                    [6] Ver Contas do Cliente \s
                    [7] Entrar em uma Conta
                """);

        opcao = scan.nextInt();

        switch (opcao) {
            case 1:
                boolean returnCadastar = this.menuCadastrar(scan);

                if(!returnCadastar) {
                    System.out.println("Houve um problema ao cadastrar Cliente");
                    break;
                }

                System.out.println("Cliente cadastrado com sucesso!");

                break;
            case 2:
                this.menuListarTodos();

                break;
            case 3:
                this.menuBuscarPorId(scan);

                break;
            case 4:
                boolean returnEditar = this.menuEditar(scan);

                if(!returnEditar) {
                    System.out.println("Houve um problema ao editar Cliente");
                    break;
                }

                System.out.println("Cliente editado com sucesso");

                break;
            case 5:
                boolean returnExcluir = this.menuExcluir(scan);

                if(!returnExcluir) {
                    System.out.println("Houve um problema ao excluir Cliente");
                    break;
                }

                System.out.println("Cliente excluido com sucesso");

                break;
            case 6:
                this.menuListarContas(scan);

                break;
            case 7:
                this.menuEntrarNaConta(scan,contaController);

                break;
            default:
                break;
        }
    }

    private boolean menuCadastrar(Scanner scan) {
        String nome;
        String email;
        String cpf;

        System.out.println("Digite o nome do Cliente");
        nome = scan.next();

        System.out.println("Digite o email do Cliente");
        email = scan.next();

        System.out.println("Digite o cpf do Cliente");
        cpf =   scan.next();

        Cliente cliente = new Cliente(nome, email, cpf);

        return this.criar(cliente);
    }
    protected void menuListarTodos() {
        for (Cliente cliente: this.listarTodos()) {
            System.out.printf("""
                        Id: %d \s
                        Nome: %s \s
                        Email: %s \s
                        Cpf: %s \s
                    """, cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf());
        }
    }
    private void menuBuscarPorId(Scanner scan) {
        int idCliente;
        Cliente cliente;

        do {
            System.out.println("Digite o id do cliente que deseja buscar");
            idCliente = scan.nextInt();

            cliente = this.buscarPorId(idCliente);

            if(cliente == null)
                System.out.printf("Cliente não encontrado com o id %d, digite novamente \n", idCliente);
        } while(cliente == null);

        System.out.printf("""
                    Id: %d \s
                    Nome: %s \s
                    Email: %s \s
                    Cpf: %s
                """, cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf());
    }
    private boolean menuEditar(Scanner scan) {
        int idCliente;
        int campos;
        Cliente cliente;

        do {
            this.menuListarTodos();

            System.out.println("Digite o id do Cliente que deseja editar");
            idCliente = scan.nextInt();

            cliente = this.buscarPorId(idCliente);

            if(cliente == null)
                System.out.printf("Cliente não encontrado com o id %d, digite novamento \n", idCliente);
        } while (cliente == null);

        System.out.printf("""
                    Id: %d \s
                    Nome: %s \s
                    Email: %s \s
                    Cpf: %s
                """, cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf());

        System.out.println("""
                    Quantos campos deseja editar? \s
                    [1] Um \s
                    [2] Dois \s
                    [3] Todos
                """);

        campos = scan.nextInt();

        switch (campos) {
            case 1:
                int opcao;

                System.out.println("""
                    Selecione o campo que deseja alterar \s
                    [1] Nome \s
                    [2] Email \s
                    [3] Cpf \s
                    [4] Todos
                """);

                opcao = scan.nextInt();

                switch (opcao) {
                    case 1:
                        String nome;

                        System.out.println("Digite o novo nome");
                        nome = scan.next();

                        cliente.setNome(nome);
                        break;
                    case 2:
                        String email;

                        System.out.println("Digite o nome email");
                        email = scan.next();

                        cliente.setEmail(email);
                        break;
                    case 3:
                        String cpf;

                        System.out.println("Digite o novo cpf");
                        cpf = scan.next();

                        cliente.setCpf(cpf);
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                int opcao1;
                int opcao2;

                System.out.println("""
                    Selecione o primeiro campo que deseja alterar \s
                    [1] Nome \s
                    [2] Email \s
                    [3] Cpf
                """);

                opcao1 = scan.nextInt();

                switch (opcao1) {
                    case 1:
                        String nome;

                        System.out.println("Digite o novo nome");
                        nome = scan.next();

                        System.out.println("""
                            Selecione o segundo campo que deseja alterar \s
                            [1] Email \s
                            [2] Cpf
                        """);
                        opcao2 = scan.nextInt();

                        if(opcao2 == 1) {
                            String email;

                            System.out.println("Digite o novo email");
                            email = scan.next();

                            cliente.setNome(nome);
                            cliente.setEmail(email);
                        } else if(opcao2 == 2) {
                            String cpf;

                            System.out.println("Digite o novo cpf");
                            cpf = scan.next();

                            cliente.setNome(nome);
                            cliente.setCpf(cpf);
                        }
                        break;
                    case 2:
                        String email;

                        System.out.println("Digite o novo email");
                        email = scan.next();

                        System.out.println("""
                            Selecione o segundo campo que deseja alterar \s
                            [1] Nome \s
                            [2] Cpf
                        """);
                        opcao2 = scan.nextInt();

                        if(opcao2 == 1) {
                            String nomeOp2;

                            System.out.println("Digite o novo nome");
                            nomeOp2 = scan.next();

                            cliente.setEmail(email);
                            cliente.setNome(nomeOp2);

                        } else if (opcao2 == 2) {
                            String cpf;

                            System.out.println("Digite o novo cpf");
                            cpf = scan.next();

                            cliente.setEmail(email);
                            cliente.setCpf(cpf);
                        }
                        break;
                    case 3:
                        String cpf;

                        System.out.println("Digite o novo cpf");
                        cpf = scan.next();

                        System.out.println("""
                            Selecione o segundo campo que deseja alterar \s
                            [1] Nome \s
                            [2] Email
                        """);
                        opcao2 = scan.nextInt();

                        if(opcao2 == 1) {
                            String nomeOp3;

                            System.out.println("Digite o novo nome");
                            nomeOp3 = scan.next();

                            cliente.setCpf(cpf);
                            cliente.setNome(nomeOp3);
                        } else if (opcao2 == 2) {
                            String emailOp3;

                            System.out.println("Digite o novo email");
                            emailOp3 = scan.next();

                            cliente.setCpf(cpf);
                            cliente.setEmail(emailOp3);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                String nome;
                String email;
                String cpf;

                System.out.println("Digite o novo nome");
                nome = scan.next();

                System.out.println("Digite o novo email");
                email = scan.next();

                System.out.println("Digite o novo cpf");
                cpf = scan.next();

                cliente.setNome(nome);
                cliente.setEmail(email);
                cliente.setCpf(cpf);

                break;
            default:
                break;
        }

        return this.alterar(cliente);
    }
    private boolean menuExcluir(Scanner scan) {
        int idCliente;

        this.menuListarTodos();
        System.out.println("Digite o id do Cliente que deseja excluir");
        idCliente = scan.nextInt();

        return this.excluir(idCliente);
    }
    private void menuListarContas(Scanner scan) {
        int idCliente;

        this.menuListarTodos();

        System.out.println("Digite o id do cliente que deseja visualizar contas");
        idCliente = scan.nextInt();

        for(Conta conta:this.listarContas(idCliente)) {
            System.out.printf("""
                        Id: %d \s
                        Numero: %d \s
                        Tipo: %s \s
                        Saldo: %f \s
                        Limite: %f \s
                        Banco: %s
                    """, conta.getId(), conta.getNumero(), conta.getTipo().getNome(), conta.getSaldo(), conta.getLimite(), conta.getBanco().getNome());
        }
    }

    private void menuEntrarNaConta(Scanner scan, ContaController contaController) {
        int idCliente;
        Cliente cliente;
        int idConta;
        Conta conta;

        do {
            this.menuListarTodos();

            System.out.println("Digite o id do cliente que deseja acessar uma conta");
            idCliente = scan.nextInt();

            cliente = this.buscarPorId(idCliente);

            if(cliente == null)
                System.out.printf("Cliente não encontrado com o id %d, digite novamente \n", idCliente);
        } while(cliente == null);

        do {
            for(Conta contaParams:this.listarContas(idCliente)) {
                System.out.printf("""
                            Id: %d \s
                            Numero: %d \s
                            Saldo: %f \s
                            Limite: %f \s
                            Banco: %s \s
                        """, contaParams.getId(), contaParams.getNumero(), contaParams.getSaldo(), contaParams.getLimite(), contaParams.getBanco().getNome());
            }

            System.out.println("Digite o id da conta que deseja acessar");
            idConta = scan.nextInt();

            conta = contaController.buscarPorId(idConta);

            if(conta == null)
                System.out.printf("Conta não encontrada com o id %d, digite novamente \n", idConta);
        } while(conta == null);

        boolean returnAcao = this.acoesConta(scan, idConta, contaController);

        if(!returnAcao) {
            System.out.println("Houve um problema em finalizar a ação");
            return;
        }

        System.out.println("Ação finalizada com sucesso");
    }

    private boolean acoesConta(Scanner scan, Integer id, ContaController contaController) {
        int opcao;
        Double valor;

        System.out.println("""
                    Selecione uma opção \s
                    [1] Depositar \s
                    [2] Sacar \s
                    [3] Efetuar Pagamento \s
                    [4] Excluir Conta
                """);
        opcao = scan.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Digite o valor do deposito");
                valor = scan.nextDouble();

                return contaController.depositar(valor, id);
            case 2:
                System.out.println("Digite o valor do saque");
                valor = scan.nextDouble();

                return contaController.sacar(valor, id);
            case 3:
                System.out.println("Olá, está parte ainda não está pronta");
                return false;
            case 4:
                return contaController.excluir(id);
            default:
                return false;
        }
    }
}
