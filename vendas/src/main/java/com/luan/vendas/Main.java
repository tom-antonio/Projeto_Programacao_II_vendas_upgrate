package com.luan.vendas;

import java.util.Date;
import java.util.List;

import com.luan.vendas.controller.CategoriaController;
import com.luan.vendas.controller.ClienteController;
import com.luan.vendas.controller.CompraController;
import com.luan.vendas.controller.FornecedorController;
import com.luan.vendas.controller.FornecedorProdutoController;
import com.luan.vendas.controller.ProdutoController;
import com.luan.vendas.controller.VendaController;
import com.luan.vendas.model.CompraProduto;
import com.luan.vendas.model.ProdutoVenda;

public class Main {

    public static void main(String[] args) {
        CategoriaController categoriaController = new CategoriaController();
        ClienteController clienteController = new ClienteController();
        FornecedorController fornecedorController = new FornecedorController();
        FornecedorProdutoController fornecedorProdutoController = new FornecedorProdutoController();
        CompraController compraController = new CompraController();
        ProdutoController produtoController = new ProdutoController();
        VendaController vendaController = new VendaController();

        categoriaController.salvarCategoria(1, "Bebidas");
        categoriaController.salvarCategoria(2, "Frutas");
        categoriaController.salvarCategoria(3, "Laticínios");

        clienteController.salvarCliente(1, "João Silva", "111.222.333-44", "5522113", "Rua Minas Gerais, 123", "649999-5555");
        clienteController.salvarCliente(2, "Maria Oliveira", "555.666.777-88", "5522444", "Avenida Brasil, 456", "649999-6666");
        clienteController.salvarCliente(3, "Carlos Pereira", "999.000.111-22", "5522777", "Rua São Paulo, 789", "649999-7777");

        fornecedorController.salvarFornecedor(1, "Coca-Cola", "Coca-Cola Company", "12.345.678/0001-90");
        fornecedorController.salvarFornecedor(2, "Frutas do Cerrado", "Frutas do Cerrado Ltda", "98.765.432/0001-10");
        fornecedorController.salvarFornecedor(3, "Laticínios Brasil", "Laticínios Brasil S.A.", "56.789.012/0001-20");

        produtoController.salvarProduto(1, "Coca-Cola 2L", 10, 1);
        produtoController.salvarProduto(2, "Suco de Laranja 1L", 20, 1);
        produtoController.salvarProduto(3, "Maçã", 50, 2);
        produtoController.salvarProduto(4, "Banana", 100, 2);
        produtoController.salvarProduto(5, "Leite Integral 1L", 30, 3);
        produtoController.salvarProduto(6, "Queijo Mussarela 500g", 15, 3);

        fornecedorProdutoController.salvarFornecedorProduto(1, 1, 1);
        fornecedorProdutoController.salvarFornecedorProduto(2, 1, 2);
        fornecedorProdutoController.salvarFornecedorProduto(3, 2, 3);
        fornecedorProdutoController.salvarFornecedorProduto(4, 2, 4);
        fornecedorProdutoController.salvarFornecedorProduto(5, 3, 5);
        fornecedorProdutoController.salvarFornecedorProduto(6, 3, 6);

        vendaController.salvarVenda(1, new Date(), 50.0, 1, List.of(
            new ProdutoVenda(1, 1, 1, 2, 0.0),
            new ProdutoVenda(2, 1, 3, 5, 0.0)
        ));
        vendaController.salvarVenda(2, new Date(), 30.0, 2, List.of(
            new ProdutoVenda(3, 2, 2, 1, 0.0),
            new ProdutoVenda(4, 2, 4, 10, 0.0)
        ));
        vendaController.salvarVenda(3, new Date(), 40.0, 3, List.of(
            new ProdutoVenda(5, 3, 5, 3, 0.0),
            new ProdutoVenda(6, 3, 6, 2, 0.0)
        ));
        vendaController.salvarVenda(4, new Date(), 20.0, 1, List.of(
            new ProdutoVenda(7, 4, 3, 10, 0.0)
        ));
        vendaController.salvarVenda(5, new Date(), 60.0, 2, List.of(
            new ProdutoVenda(8, 5, 1, 4, 0.0),
            new ProdutoVenda(9, 5, 5, 5, 0.0)
        ));

        compraController.salvarCompra(1, new Date(), 100.0, 1, List.of(
            new CompraProduto(1, 1, 1, 20, 5.0),
            new CompraProduto(2, 1, 2, 30, 2.5)
        ));
        compraController.salvarCompra(2, new Date(), 80.0, 2, List.of(
            new CompraProduto(3, 2, 3, 50, 1.0),
            new CompraProduto(4, 2, 4, 100, 0.5)
        ));
        compraController.salvarCompra(3, new Date(), 120.0, 3, List.of(
            new CompraProduto(5, 3, 5, 40, 2.0),
            new CompraProduto(6, 3, 6, 20, 4.0)
        ));
        compraController.salvarCompra(4, new Date(), 60.0, 1, List.of(
            new CompraProduto(7, 4, 1, 10, 5.0)
        ));
        compraController.salvarCompra(5, new Date(), 90.0, 2, List.of(
            new CompraProduto(8, 5, 3, 30, 1.0),
            new CompraProduto(9, 5, 4, 50, 0.5)
        ));
    }
}