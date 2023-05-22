package com.example.demo.controller;
/*
public class StringToFornecedorConverter {

}*/

import com.example.demo.dao.FornecedorDAO;
import com.example.demo.pojo.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToFornecedorConverter implements Converter<String, Fornecedor> {
    @Autowired
    private FornecedorDAO dao;
    @Override
    public Fornecedor convert(String idTexto) {
// TODO Auto-generated method stub
        if(idTexto.isEmpty())
            return null;
        return dao.findById(Integer.parseInt(idTexto));
    }
}
