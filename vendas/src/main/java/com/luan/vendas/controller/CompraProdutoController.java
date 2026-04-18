package com.luan.vendas.controller;

import java.util.List;

import com.luan.vendas.dao.CompraProdutoDao;
import com.luan.vendas.model.CompraProduto;

public class CompraProdutoController {

    private final CompraProdutoDao compraProdutoDao;

    public CompraProdutoController() {
        this.compraProdutoDao = new CompraProdutoDao();
    }

    public String salvarCompraProduto(int compraId, int produtoId, int qtdeProduto, double valorUnit) {
        if (compraId <= 0) {
            return "ID da compra inválido.";
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

        CompraProduto compraProduto = new CompraProduto();
        compraProduto.setIdCompra(compraId);
        compraProduto.setIdProduto(produtoId);
        compraProduto.setQtdeProduto(qtdeProduto);
        compraProduto.setValorUnit(valorUnit);

        boolean salvo = compraProdutoDao.salvar(compraProduto);
        if (!salvo) {
            return "Erro ao salvar relação compra-produto no banco de dados.";
        }

        return null;
    }

    public String excluirCompraProduto(int id) {
        if (id <= 0) {
            return "ID da relação compra-produto inválido.";
        }

        boolean excluido = compraProdutoDao.excluir(id, null);
        if (!excluido) {
            return "Erro ao excluir relação compra-produto do banco de dados.";
        }

        return null;
    }

    public List<CompraProduto> listarCompraProdutos() {
        return compraProdutoDao.listarTodos();
    }
}