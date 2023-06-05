package com.example.demo.pojo;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityScan
@Table(name = "Venda")
public class Venda extends AbstractEntity<Integer>{
    @JsonProperty
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Clientes cliente;
    @JsonProperty
    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
   /*  @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;*/
    @JsonProperty
    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;



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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


    /*  public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }*/
        
}
