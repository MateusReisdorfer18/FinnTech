package br.com.FinnTech;

import br.com.FinnTech.controller.ClienteController;
import br.com.FinnTech.model.Cliente;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ClienteController clienteController = new ClienteController();

        List<Cliente> clientes = clienteController.listarTodos();

        for(Cliente cliente:clientes) {
            System.out.println(cliente.getId());
            System.out.println(cliente.getNome());
            System.out.println(cliente.getCpf());
            System.out.println(cliente.getEmail());
            System.out.println(cliente.getContas());
        }
    }
}