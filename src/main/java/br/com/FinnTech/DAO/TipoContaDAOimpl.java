package br.com.FinnTech.DAO;

import br.com.FinnTech.model.TipoConta;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoContaDAOimpl implements GenericDAO {
    private Connection conn;

    public TipoContaDAOimpl () {
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
        String query = "SELECT * FROM tipo_conta";

        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TipoConta tipoConta = new TipoConta(rs.getString("nome"));
                lista.add(tipoConta);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao listar todos");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
    }

    @Override
    public Object buscarPorId(Integer id) {
        TipoConta tipoConta = new TipoConta();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM tipo_conta WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();

            if(rs.next()) {
                tipoConta.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao buscar tipo da conta pelo id");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return tipoConta;
    }

    @Override
    public boolean criar(Object objeto) {
        PreparedStatement stmt = null;
        String query = "INSERT INTO tipo_conta(nome) VALUES(?)";

        try {
            stmt = this.conn.prepareStatement(query);
            TipoConta tipoConta = (TipoConta) objeto;
            stmt.setString(1, tipoConta.getNome());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao criar tipo conta");
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
        String query = "UPDATE tipo_conta SET nome=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            TipoConta tipoConta = (TipoConta) objeto;
            stmt.setString(1, tipoConta.getNome());
            stmt.setInt(2, tipoConta.getId());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao editar tipo conta");
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
        String query = "DELETE FROM tipo_conta WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao excluir tipo conta");
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
            System.out.println("Problemas na DAO ao fechar conxeao");
            e.printStackTrace();
        }
    }
}
