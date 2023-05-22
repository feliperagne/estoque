package com.example.demo.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
import java.util.List;

@Entity
@EntityScan
@Table(name = "clientes")
public class Clientes extends AbstractEntity<Integer> {

	@NotBlank(message = "Informe um nome válido!")
	@Column(length = 150, nullable = false, name = "nome")
	@Size(max = 50, message = "Não precisa de um nome muito grande, por favor, insira-o de forma simplificada!")
	private String nome;
	@Email(message = "Informe um email válido!")
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "imagem", length = 300)
	private String imagem;
	@NotBlank(message = "Pode ser que precisamos te ligar, então insira um telefone por favor!")
	@Size(max = 11, message = "O telefone contém apenas 11 números, não ultrapasse isso!")
	@Column(name = "Telefone", nullable = false)
	private String telefone;
	@NotBlank(message = "Precisamos da sua localização para enviarmos seu produto até você")
	@Column(name = "Logradouro",nullable = false)
	private String logradouro;
	@NotNull(message = "Informe a data de nascimento") /* OS CAMPOS DE DATA DEVEM SER NOTNULL   */
	@Column(name="DataNascimento", nullable = false, columnDefinition = "DATE")
	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "cliente")
	private List<Venda> vendas;


	public Clientes() {
		this.email = email;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

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

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	
}
