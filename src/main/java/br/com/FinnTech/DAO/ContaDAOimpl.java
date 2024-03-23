package br.com.FinnTech.DAO;

import br.com.FinnTech.controller.BancoController;
import br.com.FinnTech.controller.ClienteController;
import br.com.FinnTech.model.Banco;
import br.com.FinnTech.model.Cliente;
import br.com.FinnTech.model.Conta;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAOimpl implements GenericDAO {
    private Connection conn;
    private ClienteController clienteController = new ClienteController();
    private BancoController bancoController = new BancoController();

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
                conta.setTipo(rs.getInt("tipo"));
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
                conta.setTipo(rs.getInt("tipo"));
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
            stmt.setInt(2, conta.getTipo());
            stmt.setInt(3, conta.getNumero());
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
        String query = "UPDATE conta SET cliente=?, tipo=?, numero=?, banco=?, saldo=?, limite=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            Conta conta = (Conta) objeto;
            stmt.setInt(1, conta.getCliente().getId());
            stmt.setInt(2, conta.getTipo());
            stmt.setInt(3, conta.getNumero());
            stmt.setInt(4, conta.getBanco().getId());
            stmt.setDouble(5, conta.getSaldo());
            stmt.setDouble(6, conta.getLimite());
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
