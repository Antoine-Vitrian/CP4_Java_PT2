package br.com.fiap.delegacia.factory;

import br.com.fiap.delegacia.dao.DelegaciaDAO;
import br.com.fiap.delegacia.dao.PolicialDAO;

public class DAOFactory {

    private DAOFactory() {
    }

    public static PolicialDAO criarPolicialDAO() {
        return new PolicialDAO();
    }

    public static DelegaciaDAO criarDelegaciaDAO() {
        return new DelegaciaDAO();
    }
}