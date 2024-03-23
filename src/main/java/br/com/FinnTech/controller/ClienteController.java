package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.ClienteDAOimpl;
import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    public ClienteController() {}

    public List<Cliente> listarTodos() {
        try {
            GenericDAO dao = new ClienteDAOimpl();
            List<Cliente> clientes = new ArrayList<Cliente>();

            for(Object objeto:dao.listarTodos()) {
                clientes.add((Cliente) objeto);
            }

            return clientes;
        } catch (Exception e) {
            System.out.println("Problema no Controller ao listar todos");
            e.printStackTrace();
            return null;
        }
    }

    public Cliente buscarPorId(Integer id) {
        try {
            GenericDAO dao = new ClienteDAOimpl();
            return (Cliente) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao ");
            e.printStackTrace();
            return null;
        }
    }

    public boolean criar(Cliente cliente) {
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

    public boolean alterar(Cliente cliente) {
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

    public boolean excluir(Integer id) {
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
}
