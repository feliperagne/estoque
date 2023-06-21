package com.example.demo.pojo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityScan
@Table(name = "Venda")
public class Venda extends AbstractEntity<Integer>{

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionarios;

    @OneToMany(mappedBy = "venda")
    private List<DetalheVenda> detalheVenda;

    @Column(name = "data_da_venda", nullable = false)
    private LocalDate dataVenda;

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    @OneToMany(mappedBy = "venda")
    private List<DetalheVenda> detalheVendas;

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(Funcionario funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<DetalheVenda> getDetalheVenda() {
        return detalheVenda;
    }

    public void setDetalheVenda(List<DetalheVenda> detalheVenda) {
        this.detalheVenda = detalheVenda;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<DetalheVenda> getDetalheVendas() {
        return detalheVendas;
    }

    public void setDetalheVendas(List<DetalheVenda> detalheVendas) {
        this.detalheVendas = detalheVendas;
    }
}
