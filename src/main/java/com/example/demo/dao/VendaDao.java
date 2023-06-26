package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.pojo.Venda;

@Repository
public class VendaDao extends AbstractDao<Venda, Integer> {

    public List<Venda> findByVendas(int id) {
        List<Venda> lista = getEntityManager().createQuery("SELECT c FROM Venda u INNER JOIN u.DetalheVenda c WHERE u.id = :id", Venda.class)
                .setParameter("id", id)
                .getResultList();

        if (!lista.isEmpty()) {
            return lista;
        }

        return null;
    }

}