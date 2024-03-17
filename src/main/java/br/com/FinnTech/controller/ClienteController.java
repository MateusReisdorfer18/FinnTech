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
}
