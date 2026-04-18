package com.luan.vendas.controller;

import java.util.List;

import com.luan.vendas.dao.FornecedorDao;
import com.luan.vendas.model.Fornecedor;

public class FornecedorController {

	private final FornecedorDao fornecedorDao;

	public FornecedorController() {
		this.fornecedorDao = new FornecedorDao();
	}

	public String salvarFornecedor(String nomeFantasia, String razaoSocial, String cnpj) {
		if (nomeFantasia == null || nomeFantasia.trim().isEmpty()) {
			return "O nome fantasia do fornecedor não pode ser vazio.";
		}
		if (razaoSocial == null || razaoSocial.trim().isEmpty()) {
			return "A razão social do fornecedor não pode ser vazia.";
		}
		if (cnpj == null || cnpj.trim().isEmpty()) {
			return "O CNPJ do fornecedor não pode ser vazio.";
		}

		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome_fantasia(nomeFantasia);
		fornecedor.setRazao_social(razaoSocial);
		fornecedor.setCnpj(cnpj);

		boolean salvo = fornecedorDao.salvar(fornecedor);
		if (!salvo) {
			return "Erro ao salvar fornecedor no banco de dados.";
		}

		return null;
	}

	public String alterarFornecedor(int id, String nomeFantasia, String razaoSocial, String cnpj) {
		if (id <= 0) {
			return "ID do fornecedor inválido.";
		}
		if (nomeFantasia == null || nomeFantasia.trim().isEmpty()) {
			return "O nome fantasia do fornecedor não pode ser vazio.";
		}
		if (razaoSocial == null || razaoSocial.trim().isEmpty()) {
			return "A razão social do fornecedor não pode ser vazia.";
		}
		if (cnpj == null || cnpj.trim().isEmpty()) {
			return "O CNPJ do fornecedor não pode ser vazio.";
		}

		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setId(id);
		fornecedor.setNome_fantasia(nomeFantasia);
		fornecedor.setRazao_social(razaoSocial);
		fornecedor.setCnpj(cnpj);

		boolean alterado = fornecedorDao.alterar(fornecedor);
		if (!alterado) {
			return "Erro ao alterar fornecedor no banco de dados.";
		}

		return null;
	}

	public String excluirFornecedor(int id) {
		if (id <= 0) {
			return "ID do fornecedor inválido.";
		}

		boolean excluido = fornecedorDao.excluir(id);
		if (!excluido) {
			return "Erro ao excluir fornecedor do banco de dados.";
		}

		return null;
	}

	public List<Fornecedor> listarFornecedores() {
		return fornecedorDao.listarTodos();
	}
}
