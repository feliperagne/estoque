package com.example.demo.pojo;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Entity
@EntityScan
@Table(name = "Venda")
public class Venda extends AbstractEntity<Integer>{
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "idFornecedor")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;


    @OneToMany(mappedBy = "venda")
    private List<DetalheVenda> detalheVendas;


    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<DetalheVenda> getDetalheVendas() {
        return detalheVendas;
    }

    public void setDetalheVendas(List<DetalheVenda> detalheVendas) {
        this.detalheVendas = detalheVendas;
    }
}
