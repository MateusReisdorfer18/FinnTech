package br.com.FinnTech.controller;

import br.com.FinnTech.DAO.GenericDAO;
import br.com.FinnTech.DAO.TipoPagamentoDAOimpl;
import br.com.FinnTech.model.TipoPagamento;

import java.util.ArrayList;
import java.util.List;

public class TipoPagamentoController {
    public TipoPagamentoController() {}

    public List<TipoPagamento> listarTodos() {
        List<TipoPagamento> tipos = new ArrayList<>();

        try {
            GenericDAO dao = new TipoPagamentoDAOimpl();
            for(Object objeto:dao.listarTodos()) {
                tipos.add((TipoPagamento) objeto);
            }
        } catch (Exception e) {
            System.out.println("Problemas no controller ao listar tipos de pagamentos");
            e.printStackTrace();
        }

        return tipos;
    }

    public  TipoPagamento buscarPorId(Integer id) {
        TipoPagamento tipoPagamento = new TipoPagamento();

        try {
            GenericDAO dao = new TipoPagamentoDAOimpl();
            tipoPagamento = (TipoPagamento) dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao buscar tipo de pagamento pelo id");
            e.printStackTrace();
        }

        return tipoPagamento;
    }

    public boolean criar(TipoPagamento tipoPagamento) {
        try {
            GenericDAO dao = new TipoPagamentoDAOimpl();
            dao.criar(tipoPagamento);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao criar tipo de pagamento");
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(TipoPagamento tipoPagamento) {
        try {
            GenericDAO dao = new TipoPagamentoDAOimpl();
            dao.alterar(tipoPagamento);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao editar tipo de pagamento");
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Integer id) {
        try {
            GenericDAO dao = new TipoPagamentoDAOimpl();
            dao.excluir(id);
            return true;
        } catch (Exception e) {
            System.out.println("Problemas na DAO ao excluir tipo de pagamento");
            e.printStackTrace();
            return false;
        }
    }
}
