package com.luan.vendas.controller;

import java.util.List;

import com.luan.vendas.dao.ProdutoVendaDao;
import com.luan.vendas.model.ProdutoVenda;

public class ProdutoVendaController {

    private final ProdutoVendaDao produtoVendaDao;

    public ProdutoVendaController() {
        this.produtoVendaDao = new ProdutoVendaDao();
    }

    public String salvarProdutoVenda(int vendaId, int produtoId, int qtdeProduto, double valorUnit) {
        if (vendaId <= 0) {
            return "ID da venda inválido.";
        }
        if (produtoId <= 0) {
            return "ID do produto inválido.";
        }
        if (qtdeProduto < 0) {
            return "A quantidade do produto não pode ser menor que zero.";
        }
        if (valorUnit < 0) {
            return "O valor unitário não pode ser menor que zero.";
        }

        ProdutoVenda produtoVenda = new ProdutoVenda();
        produtoVenda.setIdVenda(vendaId);
        produtoVenda.setIdProduto(produtoId);
        produtoVenda.setQtdeProduto(qtdeProduto);
        produtoVenda.setValorUnit(valorUnit);

        boolean salvo = produtoVendaDao.salvar(produtoVenda);
        if (!salvo) {
            return "Erro ao salvar relação produto-venda no banco de dados.";
        }

        return null;
    }

    public String excluirProdutoVenda(int id) {
        if (id <= 0) {
            return "ID da relação produto-venda inválido.";
        }

        boolean excluido = produtoVendaDao.excluir(id);
        if (!excluido) {
            return "Erro ao excluir relação produto-venda do banco de dados.";
        }

        return null;
    }

    public List<ProdutoVenda> listarProdutoVendas() {
        return produtoVendaDao.listarTodos();
    }
}