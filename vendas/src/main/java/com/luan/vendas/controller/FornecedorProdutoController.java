package com.luan.vendas.controller;

import java.util.List;

import com.luan.vendas.dao.FornecedorProdutoDao;
import com.luan.vendas.model.FornecedorProduto;

public class FornecedorProdutoController {

	private final FornecedorProdutoDao fornecedorProdutoDao;

	public FornecedorProdutoController() {
		this.fornecedorProdutoDao = new FornecedorProdutoDao();
	}

	public String salvarFornecedorProduto(int fornecedorId, int produtoId) {
		if (fornecedorId <= 0) {
			return "ID do fornecedor inválido.";
		}
		if (produtoId <= 0) {
			return "ID do produto inválido.";
		}

		FornecedorProduto fornecedorProduto = new FornecedorProduto();
		fornecedorProduto.setIdFornecedor(fornecedorId);
		fornecedorProduto.setIdProduto(produtoId);

		boolean salvo = fornecedorProdutoDao.salvar(fornecedorProduto);
		if (!salvo) {
			return "Erro ao salvar relação fornecedor-produto no banco de dados.";
		}

		return null;
	}

	public String excluirFornecedorProduto(int id) {
		if (id <= 0) {
			return "ID da relação fornecedor-produto inválido.";
		}

		boolean excluido = fornecedorProdutoDao.excluir(id);
		if (!excluido) {
			return "Erro ao excluir relação fornecedor-produto do banco de dados.";
		}

		return null;
	}

	public List<FornecedorProduto> listarFornecedorProdutos() {
		return fornecedorProdutoDao.listarTodos();
	}
}
