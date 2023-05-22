package com.example.demo.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Entity
@EntityScan
@Table(name="categoria")
public class Categoria extends AbstractEntity<Integer> {
		@NotBlank(message="Informe a descrição!")
		@Size(min = 3, max = 150, message = "A descrição deve conter 3 a 150 caracteres!")
		@Column(length = 150, nullable = false)
		private String descricao;

		@OneToMany(mappedBy = "categoria")
		private List<Produto> produtos;


	public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}


	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
