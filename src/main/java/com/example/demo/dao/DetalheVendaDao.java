package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.pojo.DetalheVenda;

import jakarta.persistence.TypedQuery;

@Repository
public class DetalheVendaDao extends AbstractDao <DetalheVenda, Integer> {
    

      public List<DetalheVenda> findBySaleId(Integer saleId) {
        String queryStr = "SELECT dv FROM DetalheVenda dv WHERE dv.venda.id = :saleId";
        TypedQuery<DetalheVenda> query = getEntityManager().createQuery(queryStr, DetalheVenda.class);
        query.setParameter("saleId", saleId);
        return query.getResultList();
    }
}
