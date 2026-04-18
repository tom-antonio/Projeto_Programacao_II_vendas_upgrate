package com.luan.vendas.controller;
import java.util.List;

import com.luan.vendas.dao.CategoriaDao;
import com.luan.vendas.model.Categoria;

public class CategoriaController {
    
    private final CategoriaDao categoriaDao;

    public CategoriaController() {
        this.categoriaDao = new CategoriaDao();
    }
    
    public String salvarCategoria (String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome da categoria não pode ser vazio.";
        }

        Categoria categoria= new Categoria();
        categoria.setNome(nome);

        boolean salvo = categoriaDao.salvar(categoria);
        if (!salvo) {
            return "Erro ao salvar categoria no banco de dados.";
        }

        return null; // null indica sucesso
    }

    public String alterarCategoria(int id, String nome) {
        if (id <= 0) {
            return "ID da categoria inválido.";
        }
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome da categoria não pode ser vazio.";
        }

        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(nome);

        boolean alterado = categoriaDao.alterar(categoria);
        if (!alterado) {
            return "Erro ao alterar categoria no banco de dados.";
        }

        return null; // null indica sucesso
    }

    public String excluirCategoria(int id) {
        if (id <= 0) {
            return "ID da categoria inválido.";
        }

        boolean excluido = categoriaDao.excluir(id);
        if (!excluido) {
            return "Erro ao excluir categoria do banco de dados.";
        }

        return null; // null indica sucesso
    }

    public List<Categoria> pesquisarCategoria() {
        return categoriaDao.listarTodos();
    }
}