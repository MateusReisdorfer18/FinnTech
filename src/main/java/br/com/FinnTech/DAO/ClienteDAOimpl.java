package br.com.FinnTech.DAO;

import br.com.FinnTech.controller.BancoController;
import br.com.FinnTech.controller.TipoContaController;
import br.com.FinnTech.model.Banco;
import br.com.FinnTech.model.Cliente;
import br.com.FinnTech.model.Conta;
import br.com.FinnTech.model.TipoConta;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOimpl implements GenericDAO {
    private Connection conn;
    private TipoContaController tipoContaController = new TipoContaController();
    private BancoController bancoController = new BancoController();
    public ClienteDAOimpl() throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public List<Object> listarTodos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cliente";
        List<Object> lista = new ArrayList<>();

        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCpf(rs.getString("cpf"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao listar todos os clientes");
            e.printStackTrace();
            return null;
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
    }

    @Override
    public Object buscarPorId(Integer id) {
        Cliente cliente = new Cliente();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cliente WHERE id = ?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCpf(rs.getString("cpf"));
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao buscar cliente pelo id");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return cliente;
    }

    @Override
    public boolean criar(Object objeto) {
        PreparedStatement stmt = null;
        String query = "INSERT INTO cliente(nome, email, cpf) VALUES(?, ?, ?)";

        try {
            stmt = this.conn.prepareStatement(query);
            Cliente cliente = (Cliente) objeto;
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCpf());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao cadastrar cliente");
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
        String query = "UPDATE cliente SET nome=?, email=?, cpf=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            Cliente cliente = (Cliente) objeto;
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCpf());
            stmt.setInt(4, cliente.getId());
            stmt.execute();
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao editar cliente");
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
        String query = "DELETE FROM cliente WHERE id = ?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao excluir cliente");
            e.printStackTrace();
            return false;
        } finally {
            this.fecharConexao(this.conn, stmt, null);
        }

        return true;
    }

    public List<Object> listarContas(Integer id) {
        List<Object> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM conta WHERE cliente=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Conta conta = new Conta();
                conta.setId(rs.getInt("id"));
                TipoConta tipoConta = tipoContaController.buscarPorId(rs.getInt("tipo"));
                conta.setTipo(tipoConta);
                conta.setNumero(rs.getInt("numero"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setLimite(rs.getDouble("limite"));
                Banco banco = bancoController.buscarPorId(rs.getInt("banco"));
                conta.setBanco(banco);
                lista.add(conta);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao listar contas do cliente");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
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
