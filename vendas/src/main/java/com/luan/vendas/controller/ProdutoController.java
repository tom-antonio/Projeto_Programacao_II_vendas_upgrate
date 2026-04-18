package com.luan.vendas.controller;

import java.util.List;

import com.luan.vendas.dao.ProdutoDao;
import com.luan.vendas.model.Categoria;
import com.luan.vendas.model.Produto;

public class ProdutoController {

	private final ProdutoDao produtoDao;

	public ProdutoController() {
		this.produtoDao = new ProdutoDao();
	}

	public String salvarProduto(String nome, double preco, double qtdeEstoque, int categoriaId) {
		if (nome == null || nome.trim().isEmpty()) {
			return "O nome do produto não pode ser vazio.";
		}
		if (preco < 0) {
			return "O preço do produto não pode ser menor que zero.";
		}
		if (qtdeEstoque < 0) {
			return "A quantidade em estoque não pode ser menor que zero.";
		}
		if (categoriaId <= 0) {
			return "ID da categoria inválido.";
		}

		Categoria categoria = new Categoria();
		categoria.setId(categoriaId);

		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setPreco(preco);
		produto.setQtde_estoque(qtdeEstoque);
		produto.setCategoria(categoria);

		boolean salvo = produtoDao.salvar(produto);
		if (!salvo) {
			return "Erro ao salvar produto no banco de dados.";
		}

		return null;
	}

	public String alterarProduto(int id, String nome, double preco, double qtdeEstoque, int categoriaId) {
		if (id <= 0) {
			return "ID do produto inválido.";
		}
		if (nome == null || nome.trim().isEmpty()) {
			return "O nome do produto não pode ser vazio.";
		}
		if (preco < 0) {
			return "O preço do produto não pode ser menor que zero.";
		}
		if (qtdeEstoque < 0) {
			return "A quantidade em estoque não pode ser menor que zero.";
		}
		if (categoriaId <= 0) {
			return "ID da categoria inválido.";
		}

		Categoria categoria = new Categoria();
		categoria.setId(categoriaId);

		Produto produto = new Produto();
		produto.setId(id);
		produto.setNome(nome);
		produto.setPreco(preco);
		produto.setQtde_estoque(qtdeEstoque);
		produto.setCategoria(categoria);

		boolean alterado = produtoDao.alterar(produto);
		if (!alterado) {
			return "Erro ao alterar produto no banco de dados.";
		}

		return null;
	}

	public String excluirProduto(int id) {
		if (id <= 0) {
			return "ID do produto inválido.";
		}

		boolean excluido = produtoDao.excluir(id);
		if (!excluido) {
			return "Erro ao excluir produto do banco de dados.";
		}

		return null;
	}

	public List<Produto> pesquisarProduto() {
		return produtoDao.listarTodos();
	}
}