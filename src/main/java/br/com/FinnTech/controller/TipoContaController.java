package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.DAO.TipoContaDAOimpl;
import br.com.FinnTech.model.TipoConta;

import java.util.ArrayList;
import java.util.List;

public class TipoContaController {
    public TipoContaController() {}

    public List<TipoConta> listarTodos() {
        List<TipoConta> tipos = new ArrayList<>();

        try {
            GenericDAO dao = new TipoContaDAOimpl();

            for(Object objeto:dao.listarTodos()) {
                tipos.add((TipoConta) objeto);
            }
        } catch (Exception e) {
            System.out.println("Problemas no controller ao listar todos os tipo conta");
            e.printStackTrace();
        }

        return tipos;
    }

    public TipoConta buscarPorId(Integer id) {
        TipoConta tipoConta = new TipoConta();

        try {
            GenericDAO dao = new TipoContaDAOimpl();
            tipoConta = (TipoConta) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao buscar tipo conta pelo id");
            e.printStackTrace();
        }

        return tipoConta;
    }

    public boolean criar(TipoConta tipoConta) {
        try {
            GenericDAO dao = new TipoContaDAOimpl();
            dao.criar(tipoConta);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao criar conta");
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(TipoConta tipoConta) {
        try {
            GenericDAO dao = new TipoContaDAOimpl();
            dao.alterar(tipoConta);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao editar tipo conta");
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Integer id) {
        try {
            GenericDAO dao = new TipoContaDAOimpl();
            dao.excluir(id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao excluir tipo conta");
            e.printStackTrace();
            return false;
        }
    }
}
