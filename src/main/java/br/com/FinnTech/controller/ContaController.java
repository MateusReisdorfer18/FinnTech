package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.ClienteDAOimpl;
import br.com.FinnTech.DAO.ContaDAOimpl;
import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaController {
    public ContaController() {}

    public List<Conta> listarTodos() {
        try {
            GenericDAO dao = new ContaDAOimpl();
            List<Conta> contas = new ArrayList<>();

            for(Object objeto:dao.listarTodos()) {
                contas.add((Conta) objeto);
            }

            return contas;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao listar todas as contas");
            e.printStackTrace();
            return null;
        }
    }

    public Conta buscarPorId(Integer id) {
        try {
            GenericDAO dao = new ContaDAOimpl();
             return (Conta) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao buscar conta pelo id");
            e.printStackTrace();
            return null;
        }
    }

    public boolean cadastrar(Conta conta) {
        try {
            GenericDAO dao = new ContaDAOimpl();
            dao.criar(conta);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao cadastrar conta");
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Conta conta) {
        try {
            GenericDAO dao = new ContaDAOimpl();
            dao.alterar(conta);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao editar banco");
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Integer id) {
        try {
            GenericDAO dao = new ContaDAOimpl();
            dao.excluir(id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao excluir conta");
            e.printStackTrace();
            return false;
        }
    }
}
