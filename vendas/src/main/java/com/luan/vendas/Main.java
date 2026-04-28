package com.luan.vendas;

import java.util.ArrayList;
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

        // 1. Inserir Categorias
        System.out.println("=== Inserindo Categorias ===");
        validar(categoriaController.salvarCategoria("Eletronicos"));
        validar(categoriaController.salvarCategoria("Eletrodomesticos"));
        validar(categoriaController.salvarCategoria("Moveis"));

        // 2. Inserir Clientes
        System.out.println("\n=== Inserindo Clientes ===");
        validar(clienteController.salvarCliente("Joao Silva", "123.456.789-00", "MG-12.345.678", "Rua A, 123",
            "(11) 98765-4321"));
        validar(clienteController.salvarCliente("Maria Souza", "987.654.321-00", "SP-87.654.321", "Avenida B, 456",
            "(21) 91234-5678"));

        // 3. Inserir Fornecedores
        System.out.println("\n=== Inserindo Fornecedores ===");
        validar(fornecedorController.salvarFornecedor("Tech Supplies", "Tech Supplies LTDA", "12.345.678/0001-99"));
        validar(fornecedorController.salvarFornecedor("Gadget World", "Gadget World S.A.", "98.765.432/0001-88"));

        // 4. Inserir Produtos (com categoria existente)
        System.out.println("\n=== Inserindo Produtos ===");
        validar(produtoController.salvarProduto("Smartphone", 1500.00, 2.0, 1400.00, 1550.00, 1));
        validar(produtoController.salvarProduto("Notebook", 3500.00, 1.0, 3300.00, 3600.00, 1));
        validar(produtoController.salvarProduto("Tablet", 1200.00, 3.0, 1100.00, 1250.00, 1));

        // 5. Vincular Fornecedores aos Produtos
        System.out.println("\n=== Criando vínculo Fornecedor-Produto ===");
        validar(fornecedorProdutoController.salvarFornecedorProduto(1, 1));
        validar(fornecedorProdutoController.salvarFornecedorProduto(1, 2));
        validar(fornecedorProdutoController.salvarFornecedorProduto(2, 3));

        // 6. Inserir Compra com itens
        System.out.println("\n=== Criando Compra ===");
        List<CompraProduto> compraProdutos = new ArrayList<>();
        compraProdutos.add(new CompraProduto(0, 0, 1, 10, 1400.00));
        compraProdutos.add(new CompraProduto(0, 0, 3, 5, 1100.00));

        double valorTotalCompra = 0.0;
        for (CompraProduto item : compraProdutos) {
            valorTotalCompra += item.getQtdeProduto() * item.getValorUnit();
        }

        validar(compraController.salvarCompra(new Date(), valorTotalCompra, 1, compraProdutos));

        // 7. Criar venda com produtos (usando IDs que existem no banco)
        System.out.println("\n=== Criando Venda ===");
        ProdutoVenda vp1 = new ProdutoVenda(0, 0, 1, 2, 1500.00);
        ProdutoVenda vp2 = new ProdutoVenda(0, 0, 2, 1, 3500.00);
        ProdutoVenda vp3 = new ProdutoVenda(0, 0, 3, 3, 1200.00);

        List<ProdutoVenda> vendaProdutos = new ArrayList<>();
        vendaProdutos.add(vp1);
        vendaProdutos.add(vp2);
        vendaProdutos.add(vp3);

        double valorTotal = (vp1.getQtdeProduto() * vp1.getValorUnit())
                + (vp2.getQtdeProduto() * vp2.getValorUnit())
                + (vp3.getQtdeProduto() * vp3.getValorUnit());

        // Salvar venda para o primeiro cliente inserido (ID 1)
        validar(vendaController.salvarVenda(new Date(), valorTotal, 1, vendaProdutos));
    }

    private static void validar(String erro) {
        if (erro == null) {
            return;
        }

        throw new IllegalStateException(erro);
    }
}
