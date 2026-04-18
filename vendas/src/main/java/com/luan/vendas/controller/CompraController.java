package com.luan.vendas.controller;

import java.util.Date;
import java.util.List;

import com.luan.vendas.dao.CompraDao;
import com.luan.vendas.model.Compra;
import com.luan.vendas.model.CompraProduto;
import com.luan.vendas.model.Fornecedor;

public class CompraController {

    private final CompraDao compraDao;

    public CompraController() {
        this.compraDao = new CompraDao();
    }

    public String salvarCompra(Date dataCompra, double valorTotal, int fornecedorId, List<CompraProduto> compraProdutos) {
        if (dataCompra == null) {
            return "A data da compra não pode ser vazia.";
        }
        if (valorTotal < 0) {
            return "O valor total da compra não pode ser menor que zero.";
        }
        if (fornecedorId <= 0) {
            return "ID do fornecedor inválido.";
        }
        if (compraProdutos == null || compraProdutos.isEmpty()) {
            return "A compra precisa ter pelo menos um produto.";
        }

        for (CompraProduto compraProduto : compraProdutos) {
            if (compraProduto == null) {
                return "Existe um item de compra inválido.";
            }
            if (compraProduto.getIdProduto() <= 0) {
                return "ID do produto inválido na compra.";
            }
            if (compraProduto.getQtdeProduto() <= 0) {
                return "A quantidade do produto na compra deve ser maior que zero.";
            }
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(fornecedorId);

        Compra compra = new Compra();
        compra.setData_compra(dataCompra);
        compra.setValor_total(valorTotal);
        compra.setFornecedor(fornecedor);
        compra.setCompraProdutos(compraProdutos);

        boolean salvo = compraDao.salvar(compra);
        if (!salvo) {
            return "Erro ao salvar compra no banco de dados.";
        }

        return null;
    }

    public String alterarCompra(int id, Date dataCompra, double valorTotal, int fornecedorId, List<CompraProduto> compraProdutos) {
        if (id <= 0) {
            return "ID da compra inválido.";
        }
        if (dataCompra == null) {
            return "A data da compra não pode ser vazia.";
        }
        if (valorTotal < 0) {
            return "O valor total da compra não pode ser menor que zero.";
        }
        if (fornecedorId <= 0) {
            return "ID do fornecedor inválido.";
        }
        if (compraProdutos == null || compraProdutos.isEmpty()) {
            return "A compra precisa ter pelo menos um produto.";
        }

        for (CompraProduto compraProduto : compraProdutos) {
            if (compraProduto == null) {
                return "Existe um item de compra inválido.";
            }
            if (compraProduto.getIdProduto() <= 0) {
                return "ID do produto inválido na compra.";
            }
            if (compraProduto.getQtdeProduto() <= 0) {
                return "A quantidade do produto na compra deve ser maior que zero.";
            }
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(fornecedorId);

        Compra compra = new Compra();
        compra.setId(id);
        compra.setData_compra(dataCompra);
        compra.setValor_total(valorTotal);
        compra.setFornecedor(fornecedor);
        compra.setCompraProdutos(compraProdutos);

        boolean alterado = compraDao.alterar(compra);
        if (!alterado) {
            return "Erro ao alterar compra no banco de dados.";
        }

        return null;
    }

    public String excluirCompra(int id) {
        if (id <= 0) {
            return "ID da compra inválido.";
        }

        boolean excluido = compraDao.excluir(id);
        if (!excluido) {
            return "Erro ao excluir compra do banco de dados.";
        }

        return null;
    }

    public List<Compra> pesquisarCompra() {
        return compraDao.listarTodos();
    }
}