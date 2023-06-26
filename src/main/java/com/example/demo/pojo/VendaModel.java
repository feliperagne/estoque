package com.example.demo.pojo;

import java.util.List;

public class VendaModel {

    public String id;
    public String dataVenda;
    public int idCliente;
    public int idFuncionario;
    public List<ProdutosModel> produtos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getidFuncionario() {
        return idFuncionario;
    }

    public void setidFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public List<ProdutosModel> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutosModel> produtos) {
        this.produtos = produtos;
    }
}
