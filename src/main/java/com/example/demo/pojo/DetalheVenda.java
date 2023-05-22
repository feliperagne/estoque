package com.example.demo.pojo;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@EntityScan
@Table(name = "DetalheVenda")
public class DetalheVenda extends AbstractEntity<Integer> {

    @ManyToOne
    @JoinColumn(name = "idVenda")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "idProduto")
    private Produto produto;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
