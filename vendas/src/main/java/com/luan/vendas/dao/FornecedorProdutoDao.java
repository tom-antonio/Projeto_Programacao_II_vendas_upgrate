package com.luan.vendas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.luan.vendas.model.FornecedorProduto;

public class FornecedorProdutoDao {

    public boolean salvar(FornecedorProduto fornecedorProduto) {
        String sql = "INSERT INTO tprodforne (fk_fornecedor, fk_produto) VALUES (?, ?)";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null
                     ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                     : null) {

            if (ps == null) {
                return false;
            }

            ps.setInt(1, fornecedorProduto.getIdFornecedor());
            ps.setInt(2, fornecedorProduto.getIdProduto());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    fornecedorProduto.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar relação fornecedor-produto: " + e.getMessage());
        }
        return false;
    }

    public List<FornecedorProduto> listarTodos() {
        List<FornecedorProduto> relacionamentos = new ArrayList<>();
        String sql = "SELECT id_prod_forne, fk_fornecedor, fk_produto FROM tprodforne ORDER BY fk_fornecedor, fk_produto";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return relacionamentos;
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FornecedorProduto fornecedorProduto = new FornecedorProduto(
                    rs.getInt("id_fornecedor_produto"),
                    rs.getInt("fk_fornecedor"),
                    rs.getInt("fk_produto")
                );
                relacionamentos.add(fornecedorProduto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar relações fornecedor-produto: " + e.getMessage());
        }
        return relacionamentos;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM tprodforne WHERE id_fornecedor_produto = ?";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return false;
            }

            ps.setInt(1, id);

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir relação fornecedor-produto: " + e.getMessage());
        }
        return false;
    }
}