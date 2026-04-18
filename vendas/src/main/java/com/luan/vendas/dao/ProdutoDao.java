package com.luan.vendas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.luan.vendas.model.Categoria;
import com.luan.vendas.model.Produto;

public class ProdutoDao {

    public boolean salvar(Produto produto) {
        String sql = "INSERT INTO tproduto (nome_produto, preco_produto, qtde_estoque, fk_produto_categoria) VALUES (?, ?, ?, ?)";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null
                     ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                     : null) {

            if (ps == null) {
                return false;
            }

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setDouble(3, produto.getQtde_estoque());
            ps.setInt(4, produto.getCategoria().getId());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar produto: " + e.getMessage());
        }
        return false;
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.id_produto, p.nome_produto, p.preco_produto, p.qtde_estoque, "
                + "c.id_categoria, c.nome_categoria "
                + "FROM tproduto p "
                + "JOIN tcategoria c ON c.id_categoria = p.fk_produto_categoria "
                + "ORDER BY p.nome_produto";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return produtos;
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nome_categoria")
                );

                Produto produto = new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome_produto"),
                        rs.getDouble("preco_produto"),
                        rs.getDouble("qtde_estoque"),
                        categoria
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public boolean alterar(Produto produto) {
        String sql = "UPDATE tproduto SET nome_produto = ?, preco_produto = ?, qtde_estoque = ?, fk_produto_categoria = ? WHERE id_produto = ?";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return false;
            }

            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setDouble(3, produto.getQtde_estoque());
            ps.setInt(4, produto.getCategoria().getId());
            ps.setInt(5, produto.getId());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar produto: " + e.getMessage());
        }
        return false;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM tproduto WHERE id_produto = ?";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return false;
            }

            ps.setInt(1, id);

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
        return false;
    }
}