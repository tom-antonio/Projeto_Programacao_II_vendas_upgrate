package com.luan.vendas.controller;

import java.util.Date;
import java.util.List;

import com.luan.vendas.dao.ProdutoDao;
import com.luan.vendas.dao.VendaDao;
import com.luan.vendas.model.Cliente;
import com.luan.vendas.model.Produto;
import com.luan.vendas.model.ProdutoVenda;
import com.luan.vendas.model.Venda;

public class VendaController {

	private final VendaDao vendaDao;
	private final ProdutoDao produtoDao;

	public VendaController() {
		this.vendaDao = new VendaDao();
		this.produtoDao = new ProdutoDao();
	}

	public String salvarVenda(Date dataVenda, double valorTotal, int clienteId, List<ProdutoVenda> produtosVenda) {
		if (dataVenda == null) {
			return "A data da venda não pode ser vazia.";
		}
		if (valorTotal < 0) {
			return "O valor total da venda não pode ser menor que zero.";
		}
		if (clienteId <= 0) {
			return "ID do cliente inválido.";
		}
		if (produtosVenda == null || produtosVenda.isEmpty()) {
			return "A venda precisa ter pelo menos um produto.";
		}

		for (ProdutoVenda produtoVenda : produtosVenda) {
			if (produtoVenda == null) {
				return "Existe um item de venda inválido.";
			}
			if (produtoVenda.getIdProduto() <= 0) {
				return "ID do produto inválido na venda.";
			}
			if (produtoVenda.getQtdeProduto() <= 0) {
				return "A quantidade do produto na venda deve ser maior que zero.";
			}
		}

		String erroEstoque = verificarEstoque(produtosVenda);
		if (erroEstoque != null) {
			return erroEstoque;
		}

		if (!alterarEstoque(produtosVenda, -1)) {
			return "Não foi possível atualizar o estoque dos produtos da venda.";
		}

		Cliente cliente = new Cliente();
		cliente.setId(clienteId);

		Venda venda = new Venda();
		venda.setData_venda(dataVenda);
		venda.setValor_total(valorTotal);
		venda.setCliente(cliente);
		venda.setProdutosVenda(produtosVenda);

		boolean salvo = vendaDao.salvar(venda);
		if (!salvo) {
			return "Erro ao salvar venda no banco de dados.";
		}

		return null;
	}

	private String verificarEstoque(List<ProdutoVenda> produtosVenda) {
		for (ProdutoVenda produtoVenda : produtosVenda) {
			Produto produtoExistente = produtoDao.pesquisar(produtoVenda.getIdProduto());
			if (produtoExistente == null) {
				return "Produto não encontrado para a venda.";
			}
			if (produtoExistente.getQtde_estoque() < 1) {
				return "Produto ID " + produtoVenda.getIdProduto() + " sem estoque disponível.";
			}
			if (produtoExistente.getQtde_estoque() < produtoVenda.getQtdeProduto()) {
				return "Estoque insuficiente para o produto ID " + produtoVenda.getIdProduto() + ".";
			}
		}
		return null;
	}

	private boolean alterarEstoque(List<ProdutoVenda> produtosVenda, int sinal) {
		for (ProdutoVenda produtoVenda : produtosVenda) {
			Produto produto = new Produto();
			produto.setId(produtoVenda.getIdProduto());

			boolean atualizado = produtoDao.atualizarEstoque(produto, sinal * produtoVenda.getQtdeProduto());
			if (!atualizado) {
				return false;
			}
		}
		return true;
	}

	public String alterarVenda(int id, Date dataVenda, double valorTotal, int clienteId, List<ProdutoVenda> produtosVenda) {
		if (id <= 0) {
			return "ID da venda inválido.";
		}
		if (dataVenda == null) {
			return "A data da venda não pode ser vazia.";
		}
		if (valorTotal < 0) {
			return "O valor total da venda não pode ser menor que zero.";
		}
		if (clienteId <= 0) {
			return "ID do cliente inválido.";
		}
		if (produtosVenda == null || produtosVenda.isEmpty()) {
			return "A venda precisa ter pelo menos um produto.";
		}

		for (ProdutoVenda produtoVenda : produtosVenda) {
			if (produtoVenda == null) {
				return "Existe um item de venda inválido.";
			}
			if (produtoVenda.getIdProduto() <= 0) {
				return "ID do produto inválido na venda.";
			}
			if (produtoVenda.getQtdeProduto() <= 0) {
				return "A quantidade do produto na venda deve ser maior que zero.";
			}
		}

		Cliente cliente = new Cliente();
		cliente.setId(clienteId);

		Venda venda = new Venda();
		venda.setId(id);
		venda.setData_venda(dataVenda);
		venda.setValor_total(valorTotal);
		venda.setCliente(cliente);
		venda.setProdutosVenda(produtosVenda);

		boolean alterado = vendaDao.alterar(venda);
		if (!alterado) {
			return "Erro ao alterar venda no banco de dados.";
		}
		return null;
	}

	public String excluirVenda(int id) {
		if (id <= 0) {
			return "ID da venda inválido.";
		}

		boolean excluido = vendaDao.excluir(id);
		if (!excluido) {
			return "Erro ao excluir venda do banco de dados.";
		}

		return null;
	}

    public Venda pesquisarVenda(int id) {
		if (id <= 0) {
			return null;
		}

		return vendaDao.pesquisar(id);
	}
}
