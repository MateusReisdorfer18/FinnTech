package br.com.FinnTech.DAO;

import br.com.FinnTech.model.Banco;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDAOimpl implements GenericDAO {
    private Connection conn;

    public BancoDAOimpl() {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao conectar no banco");
        }
    }

    @Override
    public List<Object> listarTodos() {
        List<Object> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM banco";

        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Banco banco = new Banco();
                banco.setId(rs.getInt("id"));
                banco.setNome(rs.getString("nome"));
                lista.add(banco);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao listar todos os bancos");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
    }

    @Override
    public Object buscarPorId(Integer id) {
        Banco banco = new Banco();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM banco WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                banco.setId(rs.getInt("id"));
                banco.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao buscar banco pelo id");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return banco;
    }

    @Override
    public boolean criar(Object objeto) {
        PreparedStatement stmt = null;
        String query = "INSERT INTO banco(nome) VALUES(?)";

        try {
            stmt = this.conn.prepareStatement(query);
            Banco banco = (Banco) objeto;
            stmt.setString(1, banco.getNome());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao cadastrar banco");
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
        String query = "UPDATE banco SET nome=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            Banco banco = (Banco) objeto;
            stmt.setString(1, banco.getNome());
            stmt.setInt(2, banco.getId());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao editar banco");
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
        String query = "DELETE FROM banco WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao excluir banco");
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
