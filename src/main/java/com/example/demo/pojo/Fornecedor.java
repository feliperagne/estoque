package com.example.demo.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Entity
@EntityScan
@Table(name = "fornecedor")
public class Fornecedor extends AbstractEntity<Integer> {
    @NotBlank(message = "O fornecedor precisa de um nome!")
    @Size(max = 50, message = "Use um nome menor para se identificar")
    @Column(name = "nome" , nullable = false)
    private String nome;
    @NotBlank(message = "Precisamos saber de onde a empresa nos entrega os produtos!")
    @Column(name = "logradouro", nullable = false)
    private String logradouro;
    @NotBlank(message = "Diga em qual bairro a empresa se localiza!")
    @Column(name = "bairro", nullable = false)
    private String bairro;
    @NotBlank(message = "Precisamos saber de onde a empresa vem!")
    @Column(name = "cidade", nullable = false)
    private String cidade;
    @NotBlank(message = "Precisamos saber de onde a empresa vem!")
    @Size(max = 8, message = "O cep possui 8 números!")
    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @OneToMany(mappedBy = "fornecedor")
    private List<Venda> vendas;

    @OneToMany(mappedBy = "fornecedor")
    private List<Produto> produtos;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
