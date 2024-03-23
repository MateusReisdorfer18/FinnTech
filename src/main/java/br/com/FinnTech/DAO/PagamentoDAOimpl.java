package br.com.FinnTech.DAO;

import br.com.FinnTech.controller.ContaController;
import br.com.FinnTech.model.Conta;
import br.com.FinnTech.model.Pagamento;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAOimpl implements GenericDAO {
    private Connection conn;
    private ContaController contaController = new ContaController();
    public PagamentoDAOimpl() {
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
        String query = "SELECT * FROM pagamento";

        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getInt("id"));
                pagamento.setTipo(rs.getInt("tipo"));
                Conta destinatario = contaController.buscarPorId(rs.getInt("destinatario"));
                pagamento.setDestinatario(destinatario);
                Conta remetente = contaController.buscarPorId(rs.getInt("remetente"));
                pagamento.setRemetente(remetente);
                pagamento.setValor(rs.getDouble("valor"));

                lista.add(pagamento);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao listar todos os pagamentos");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
    }

    @Override
    public Object buscarPorId(Integer id) {
        Pagamento pagamento = new Pagamento();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM pagamento WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                pagamento.setId(rs.getInt("id"));
                pagamento.setTipo(rs.getInt("tipo"));
                Conta destinatario = contaController.buscarPorId(rs.getInt("destinatario"));
                pagamento.setDestinatario(destinatario);
                Conta remetente = contaController.buscarPorId(rs.getInt("remetente"));
                pagamento.setRemetente(remetente);
                pagamento.setValor(rs.getDouble("valor"));
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao buscar pagamento pelo id");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return pagamento;
    }

    @Override
    public boolean criar(Object objeto) {
        PreparedStatement stmt = null;
        String query = "INSERT INTO pagamento(tipo, remetente, destinatario, valor) Values(?, ?, ?, ?)";

        try {
            stmt = this.conn.prepareStatement(query);
            Pagamento pagamento = (Pagamento) objeto;
            stmt.setInt(1, pagamento.getTipo());
            stmt.setInt(2, pagamento.getRemetente().getId());
            stmt.setInt(3, pagamento.getDestinatario().getId());
            stmt.setDouble(4, pagamento.getValor());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao cadastrar pagamento");
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
        String query = "UPDATE pagamento SET tipo=?, remetente=?, destinatario=?, valor=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            Pagamento pagamento = (Pagamento) objeto;
            stmt.setInt(1, pagamento.getTipo());
            stmt.setInt(2, pagamento.getRemetente().getId());
            stmt.setInt(3, pagamento.getDestinatario().getId());
            stmt.setDouble(4, pagamento.getValor());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao editar pagamento");
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
        String query = "DELETE FROM pagamento WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao excluir pagamento");
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
