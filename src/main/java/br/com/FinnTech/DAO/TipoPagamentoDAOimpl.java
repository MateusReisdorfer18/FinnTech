package br.com.FinnTech.DAO;

import br.com.FinnTech.model.Pagamento;
import br.com.FinnTech.model.TipoPagamento;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoPagamentoDAOimpl implements GenericDAO {
    private Connection conn;

    public TipoPagamentoDAOimpl() {
        try {
            this.conn = ConnectionFactory.getConnection();
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao conectar com o banco");
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> listarTodos() {
        List<Object> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM tipo_pagamento";

        try {
            stmt = this.conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TipoPagamento tipoPagamento = new TipoPagamento();
                tipoPagamento.setId(rs.getInt("id"));
                tipoPagamento.setTipo(rs.getString("tipo"));
                lista.add(tipoPagamento);
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao listar pagamentos");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return lista;
    }

    @Override
    public Object buscarPorId(Integer id) {
        TipoPagamento tipoPagamento = new TipoPagamento();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM tipo_pagamento WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                tipoPagamento.setId(rs.getInt("id"));
                tipoPagamento.setTipo(rs.getString("tipo"));
            }
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao buscar tipo do pagamento por id");
            e.printStackTrace();
        } finally {
            this.fecharConexao(this.conn, stmt, rs);
        }

        return tipoPagamento;
    }

    @Override
    public boolean criar(Object objeto) {
        PreparedStatement stmt = null;
        String query = "INSERT INTO tipo_pagamento(tipo) VALUES (?)";

        try {
            stmt = this.conn.prepareStatement(query);
            TipoPagamento tipoPagamento = (TipoPagamento) objeto;
            stmt.setString(1, tipoPagamento.getTipo());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao criar tipo de pagamento");
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
        String query = "UPDATE tipo_pagamento SET tipo=? WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            TipoPagamento tipoPagamento = (TipoPagamento) objeto;
            stmt.setString(1, tipoPagamento.getTipo());
            stmt.setInt(2, tipoPagamento.getId());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas na DAO ao editar tipo de pagamento");
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
        String query = "DELETE FROM tipo_pagamento WHERE id=?";

        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao excluir tipo de pagamento");
            e.printStackTrace();
            return false;
        } finally {
            this.fecharConexao(this.conn, stmt, null);
        }

        return true;
    }

    private void fecharConexao(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if(rs != null) {
                ConnectionFactory.closeConnection(conn, stmt, rs);
            } else {
                ConnectionFactory.closeConnection(conn, stmt);
            }
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao fechar conexao com o banco");
            e.printStackTrace();
        }
    }
}
