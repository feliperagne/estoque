package com.example.demo.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Entity
@EntityScan
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<Integer>  {

    @ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="idUsuario")
	private Users usuario;
    @NotBlank(message = "O nome é obrigatório!")
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "imagem", length = 300)
	private String imagem;
    @Column(name = "email", nullable = false)
    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Por favor, insira um endereço de e-mail válido!")
    private String email;
    @NotBlank(message = "O telefone é obrigatório!")
    @Column(name = "telefone", nullable = false)
    private String telefone;
    @NotBlank(message = "O funcionário precisa de um cargo!")
    @Column(name = "cargo", nullable = false)
    private String cargo;
    @NumberFormat(style= NumberFormat.Style.CURRENCY, pattern = "#,##0.00")
    @NotNull(message = "Especifique o salário!")
    @Column(name = "salario", nullable = false)
    private Double salario;

   /* @OneToMany(mappedBy = "funcionario")
    private List<Venda> vendas;*/

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

   /*  public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }*/
    public Users getUsuario() {
		return usuario;
	}

	public void setUsuario(Users usuario) {
		this.usuario = usuario;
	}
    public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
}
