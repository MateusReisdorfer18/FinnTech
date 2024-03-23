package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.BancoDAOimpl;
import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.model.Banco;

import java.util.ArrayList;
import java.util.List;

public class BancoController {
    public BancoController() {}

    public List<Banco> listarTodos() {
        try {
            GenericDAO dao = new BancoDAOimpl();
            List<Banco> bancos = new ArrayList<>();

            for(Object objeto:dao.listarTodos()) {
                bancos.add((Banco) objeto);
            }

            return bancos;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao listar todos os bancos");
            e.printStackTrace();
            return null;
        }
    }

    public Banco buscarPorId(Integer id) {
        try {
            GenericDAO dao = new BancoDAOimpl();

            return (Banco) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao buscar banco pelo id");
            e.printStackTrace();
            return null;
        }
    }

    public boolean cadastrar(Banco banco) {
        try {
            GenericDAO dao = new BancoDAOimpl();
            dao.criar(banco);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao cadastrar banco");
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Banco banco) {
        try {
            GenericDAO dao = new BancoDAOimpl();
            dao.alterar(banco);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao editar banco");
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Integer id) {
        try {
            GenericDAO dao = new BancoDAOimpl();
            dao.excluir(id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao excluir banco");
            e.printStackTrace();
            return false;
        }
    }
}
