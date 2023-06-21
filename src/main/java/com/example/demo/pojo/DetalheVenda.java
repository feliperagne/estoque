package com.example.demo.pojo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalhe_venda")
public class DetalheVenda extends AbstractEntity<Integer> {
    
    @ManyToOne
    @JoinColumn(name = "id_venda", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produtos;

    @Column(nullable = false)
    private int qtde;

    @Column(nullable = false)
    private BigDecimal valor_unit;

    public Venda getVendas() {
        return venda;
    }

    public void setVendas(Venda venda) {
        this.venda = venda;
    }

    public Produto getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto produtos) {
        this.produtos = produtos;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public BigDecimal getValor_unit() {
        return valor_unit;
    }

    public void setValor_unit(BigDecimal valor_unit) {
        this.valor_unit = valor_unit;
    }
}
