package br.com.FinnTech.DAO;

import br.com.FinnTech.model.Cliente;
import br.com.FinnTech.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOimpl implements GenericDAO {
    private Connection conn;

    public ClienteDAOimpl() throws Exception {
        try {
            this.conn = ConnectionFactory.getConnection();
            System.out.println("Conectado ao Banco");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public List<Object> listarTodos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM cliente";
        List<Object> lista = new ArrayList<Object>();

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
            try {
                ConnectionFactory.closeConnection(this.conn, stmt, rs);
            } catch (Exception e) {
                System.out.println("Problemas na DAO ao fechar conexao");
                e.printStackTrace();
            }
        }

        return lista;
    }

    @Override
    public Object buscarPorId(Integer id) {
        return null;
    }

    @Override
    public boolean criar(Object objeto) {
        return false;
    }

    @Override
    public boolean alterar(Object objeto) {
        return false;
    }

    @Override
    public boolean excluir(Integer id) {
        return false;
    }
}
