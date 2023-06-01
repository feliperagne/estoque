package com.example.demo.dao;

import com.example.demo.pojo.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDao extends AbstractDao<Produto, Integer>{

    @PersistenceContext
    private EntityManager entityManager;

    public boolean existsById(Integer id) {
        Produto venda = entityManager.find(Produto.class, id);
        return venda != null;
    }
}
