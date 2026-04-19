package com.luan.vendas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.luan.vendas.model.Fornecedor;

public class FornecedorDao {

    public boolean salvar(Fornecedor fornecedor) {
        String sql = "INSERT INTO tfornecedor (nome_fornecedor, razao_fornecedor, cnpj_fornecedor) VALUES (?, ?, ?)";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null
                     ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                     : null) {

            if (ps == null) {
                return false;
            }

            ps.setString(1, fornecedor.getNome_fantasia());
            ps.setString(2, fornecedor.getRazao_social());
            ps.setString(3, fornecedor.getCnpj());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    fornecedor.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar fornecedor: " + e.getMessage());
        }
        return false;
    }

    public List<Fornecedor> listarTodos() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT id_fornecedor, nome_fornecedor, razao_fornecedor, cnpj_fornecedor FROM tfornecedor ORDER BY nome_fornecedor";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return fornecedores;
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor(
                    rs.getInt("id_fornecedor"),
                    rs.getString("nome_fornecedor"),
                    rs.getString("razao_fornecedor"),
                    rs.getString("cnpj_fornecedor")
                );
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedores: " + e.getMessage());
        }
        return fornecedores;
    }

    public boolean alterar(Fornecedor fornecedor) {
        String sql = "UPDATE tfornecedor SET nome_fornecedor = ?, razao_fornecedor = ?, cnpj_fornecedor = ? WHERE id_fornecedor = ?";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return false;
            }

            ps.setString(1, fornecedor.getNome_fantasia());
            ps.setString(2, fornecedor.getRazao_social());
            ps.setString(3, fornecedor.getCnpj());
            ps.setInt(4, fornecedor.getId());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao alterar fornecedor: " + e.getMessage());
        }
        return false;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM tfornecedor WHERE id_fornecedor = ?";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return false;
            }

            ps.setInt(1, id);

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir fornecedor: " + e.getMessage());
        }
        return false;
    }
}