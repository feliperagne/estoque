package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.NumberFormat;



@Entity
@EntityScan
@Table(name = "produto")
public class Produto extends AbstractEntity<Integer> {

   
    @Column(name = "preco")
    @NumberFormat(style= NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    private Double preco;
    @NotBlank(message = "Informe um preço para o seu produto!")
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "imagem", length = 300)
    private String imagem;
    @PositiveOrZero(message = "A quantidade deve ser maior ou igual a 0!")
    @Column(name = "quantidade")
    @NumberFormat(style= NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    private Double qntd;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fornecedor")
    private Fornecedor fornecedor;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;




    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getQntd() {
        return qntd;
    }

    public void setQntd(Double qntd) {
        this.qntd = qntd;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
