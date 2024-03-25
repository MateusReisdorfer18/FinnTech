package br.com.FinnTech.DAO;

import br.com.FinnTech.controller.*;
import br.com.FinnTech.model.*;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContaDAOimpl implements GenericDAO {
    private Connection conn;
    private ClienteController clienteController = new ClienteController();
    private BancoController bancoController = new BancoController();
    private TipoContaController tipoContaController = new TipoContaController();
    private ContaController contaController = new ContaController();
    private TipoPagamentoController tipoPagamentoController = new TipoPagamentoController();

    public ContaDAOimpl() {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao conectar no banco");
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> listarTodos() {
        List<Object> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM conta";

        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Conta conta = new Conta();
                conta.setId(rs.getInt("id"));
                Cliente cliente = clienteController.buscarPorId(rs.getInt("cliente"));
                conta.setCliente(cliente);
                TipoConta tipoConta = tipoContaController.buscarPorId(rs.getInt("tipo"));
                conta.setTipo(tipoConta);
                conta.setNumero(rs.getInt("numero"));
                Banco banco = bancoController.buscarPorId(rs.getInt("banco"));
                conta.setBanco(banco);
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                lista.add(conta);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao listar todas as contas");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
    }

    @Override
    public Object buscarPorId(Integer id) {
        Conta conta = new Conta();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM conta WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                conta.setId(rs.getInt("id"));
                Cliente cliente = clienteController.buscarPorId(rs.getInt("cliente"));
                conta.setCliente(cliente);
                TipoConta tipoConta = tipoContaController.buscarPorId(rs.getInt("tipo"));
                conta.setTipo(tipoConta);
                conta.setNumero(rs.getInt("numero"));
                Banco banco = bancoController.buscarPorId(rs.getInt("banco"));
                conta.setBanco(banco);
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao buscar conta pelo id");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return conta;
    }

    @Override
    public boolean criar(Object objeto) {
        PreparedStatement stmt = null;
        String query = "INSERT INTO conta(cliente, tipo, numero, banco, saldo, limite) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            stmt = this.conn.prepareStatement(query);
            Conta conta = (Conta) objeto;
            stmt.setInt(1, conta.getCliente().getId());
            stmt.setInt(2, conta.getTipo().getId());
            stmt.setInt(3, this.gerarNumero());
            stmt.setInt(4, conta.getBanco().getId());
            stmt.setDouble(5, conta.getSaldo());
            stmt.setDouble(6, conta.getLimite());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao criar conta");
            e.printStackTrace();
            return false;
        } finally {
            this.fecharConexao(this.conn, stmt, null);
        }

        return true;
    }

    @Override
    public boolean alterar(Object objeto) {
        PreparedStatement stmt = null;
        String query = "UPDATE conta SET tipo=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            Conta conta = (Conta) objeto;
            stmt.setInt(1, conta.getTipo().getId());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao editar conta");
            e.printStackTrace();
            return false;
        } finally {
            this.fecharConexao(this.conn, stmt, null);
        }

        return true;
    }

    @Override
    public boolean excluir(Integer id) {
        PreparedStatement stmt = null;
        String query = "DELETE FROM conta WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao excluir conta");
            e.printStackTrace();
            return false;
        } finally {
            this.fecharConexao(this.conn, stmt, null);
        }

        return true;
    }

    public boolean depositar(Double valor, Integer id) {
        PreparedStatement stmt = null;
        String query = "UPDATE conta SET saldo=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setDouble(1, valor);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao fazer deposito");
            e.printStackTrace();
            return false;
        } finally {
            this.fecharConexao(this.conn, stmt, null);
        }

        return true;
    }

    public boolean sacar(Double valor, Integer id) {
        PreparedStatement stmt = null;
        String query = "UPDATE conta SET saldo=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            Double saldoAtual = this.verSaldo(id) - valor;
            stmt.setDouble(1, saldoAtual);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao efetuar saque");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Pagamento> verExtrato(Integer id) {
        List<Pagamento> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM pagamento WHERE remetente = ?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getInt("id"));
                Conta remetente = contaController.buscarPorId(rs.getInt("remetente"));
                pagamento.setRemetente(remetente);
                Conta destinatario = contaController.buscarPorId(rs.getInt("destinatario"));
                pagamento.setDestinatario(destinatario);
                TipoPagamento tipoPagamento = tipoPagamentoController.buscarPorId(rs.getInt("tipo"));
                pagamento.setTipo(tipoPagamento);
                pagamento.setValor(rs.getDouble("valor"));
                lista.add(pagamento);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao ver extrato");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
    }

    public Conta buscarPorNumero(Integer numero) {
        Conta conta = new Conta();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM conta WHERE numero = ?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, numero);
            rs = stmt.executeQuery();

            if(rs.next()) {
                conta.setId(rs.getInt("id"));
                conta.setNumero(rs.getInt("numero"));
                TipoConta tipoConta = tipoContaController.buscarPorId(rs.getInt("tipo"));
                conta.setTipo(tipoConta);
                Cliente cliente = clienteController.buscarPorId(rs.getInt("cliente"));
                conta.setCliente(cliente);
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                Banco banco = bancoController.buscarPorId(rs.getInt("banco"));
                conta.setBanco(banco);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao buscar conta pelo numero");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return conta;
    }

    private Double verSaldo(Integer id) {
        Double valor = 0.0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT saldo FROM conta WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                valor = rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao consultar salod");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return valor;
    }

    private Integer gerarNumero() {
        Double numAleatorio = Math.random() * 1000000;
        return (int) Math.floor(numAleatorio);
    }

    private void fecharConexao(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if(rs != null)
                ConnectionFactory.closeConnection(conn, stmt, rs);
            else
                ConnectionFactory.closeConnection(conn, stmt);
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao fechar conexao");
            e.printStackTrace();
        }
    }
}
