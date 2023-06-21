package com.example.demo.dao;

import com.example.demo.pojo.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDao extends AbstractDao<Produto, Integer>{

    @PersistenceContext
    private EntityManager entityManager; 

    
}
