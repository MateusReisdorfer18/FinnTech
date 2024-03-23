package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.DAO.PagamentoDAOimpl;
import br.com.FinnTech.model.Pagamento;

import java.util.ArrayList;
import java.util.List;

public class PagamentoController {
    public PagamentoController() {}

    public List<Pagamento> listarTodos() {
        try {
            GenericDAO dao = new PagamentoDAOimpl();
            List<Pagamento> pagamentos = new ArrayList<>();

            for(Object objeto:dao.listarTodos()) {
                pagamentos.add((Pagamento) objeto);
            }

            return pagamentos;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao listar todos os pagamentos");
            e.printStackTrace();
            return null;
        }
    }

    public Pagamento buscarPorId(Integer id) {
        try {
            GenericDAO dao = new PagamentoDAOimpl();
            return (Pagamento) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas no controller ao buscar pagamentos por id");
            e.printStackTrace();
            return null;
        }
    }

    public boolean cadastrar(Pagamento pagamento) {
        try {
            GenericDAO dao = new PagamentoDAOimpl();
            dao.criar(pagamento);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao cadastrar pagamento");
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Pagamento pagamento) {
        try {
            GenericDAO dao = new PagamentoDAOimpl();
            dao.alterar(pagamento);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao editar pagamento");
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Integer id) {
        try {
            GenericDAO dao = new PagamentoDAOimpl();
            dao.excluir(id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas no controller ao excluir pagamento");
            e.printStackTrace();
            return false;
        }
    }
}
