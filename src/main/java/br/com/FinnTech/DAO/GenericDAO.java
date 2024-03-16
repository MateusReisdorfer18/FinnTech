package br.com.FinnTech.DAO;

import java.util.List;

public interface GenericDAO {
    public List<Object> listarTodos();
    public Object buscarPorId(Integer id);
    public boolean criar(Object objeto);
    public boolean alterar(Object objeto);
    public boolean excluir(Integer id);
}
