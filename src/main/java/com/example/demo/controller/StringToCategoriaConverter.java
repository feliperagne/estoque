package com.example.demo.controller;
/*
public class StringToCategoriaConverter {
}
*/


import com.example.demo.dao.CategoriaDAO;
import com.example.demo.pojo.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class StringToCategoriaConverter implements Converter<String, Categoria> {
    @Autowired
    private CategoriaDAO dao;
    @Override
    public Categoria convert(String idTexto) {
// TODO Auto-generated method stub
        if(idTexto.isEmpty())
            return null;
        return dao.findById(Integer.parseInt( idTexto));
    }
}