package com.luan.vendas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.luan.vendas.model.Cliente;
import com.luan.vendas.model.ProdutoVenda;
import com.luan.vendas.model.Venda;

public class VendaDao {

    private final ProdutoVendaDao produtoVendaDao;

    public VendaDao() {
        this.produtoVendaDao = new ProdutoVendaDao();
    }

    public boolean salvar(Venda venda) {
        String sql = "INSERT INTO tvenda (data_venda, valor_total, id_cliente) VALUES (?, ?, ?)";

        try (Connection conn = Postgres.conectar()) {
            if (conn == null) {
                return false;
            }

            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, new java.sql.Date(venda.getData_venda().getTime()));
                ps.setDouble(2, venda.getValor_total());
                ps.setInt(3, venda.getCliente().getId());

                int linhasAfetadas = ps.executeUpdate();
                if (linhasAfetadas <= 0) {
                    conn.rollback();
                    return false;
                }

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    venda.setId(rs.getInt(1));
                }

                if (venda.getProdutosVenda() != null) {
                    for (ProdutoVenda item : venda.getProdutosVenda()) {
                        item.setIdVenda(venda.getId());
                        if (!produtoVendaDao.inserir(conn, item)) {
                            conn.rollback();
                            return false;
                        }
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro ao salvar venda: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar para salvar venda: " + e.getMessage());
            return false;
        }
    }

    public List<Venda> listarTodos() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT v.id_venda, v.data_venda, v.valor_total, "
                + "c.id_cliente, c.nome_cliente, c.cpf_cliente, c.rg_cliente, c.endereco_cliente, c.telefone_cliente "
                + "FROM tvenda v "
                + "JOIN tcliente c ON c.id_cliente = v.id_cliente "
                + "ORDER BY v.data_venda DESC, v.id_venda DESC";

        try (Connection conn = Postgres.conectar();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {

            if (ps == null) {
                return vendas;
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome_cliente"),
                        rs.getString("cpf_cliente"),
                        rs.getString("rg_cliente"),
                        rs.getString("endereco_cliente"),
                        rs.getString("telefone_cliente")
                );

                Venda venda = new Venda(
                        rs.getInt("id_venda"),
                        rs.getDate("data_venda"),
                        rs.getDouble("valor_total"),
                        cliente,
                        produtoVendaDao.listarPorVendaId(rs.getInt("id_venda"))
                );
                vendas.add(venda);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        }
        return vendas;
    }

    public boolean alterar(Venda venda) {
        String sql = "UPDATE tvenda SET data_venda = ?, valor_total = ?, id_cliente = ? WHERE id_venda = ?";

        try (Connection conn = Postgres.conectar()) {
            if (conn == null) {
                return false;
            }

            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, new java.sql.Date(venda.getData_venda().getTime()));
                ps.setDouble(2, venda.getValor_total());
                ps.setInt(3, venda.getCliente().getId());
                ps.setInt(4, venda.getId());

                int linhasAfetadas = ps.executeUpdate();
                if (linhasAfetadas <= 0) {
                    conn.rollback();
                    return false;
                }

                if (!produtoVendaDao.excluirPorVendaId(conn, venda.getId())) {
                    conn.rollback();
                    return false;
                }

                if (venda.getProdutosVenda() != null) {
                    for (ProdutoVenda item : venda.getProdutosVenda()) {
                        item.setIdVenda(venda.getId());
                        if (!produtoVendaDao.inserir(conn, item)) {
                            conn.rollback();
                            return false;
                        }
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro ao alterar venda: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar para alterar venda: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM tvenda WHERE id_venda = ?";

        try (Connection conn = Postgres.conectar()) {
            if (conn == null) {
                return false;
            }

            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                if (!produtoVendaDao.excluirPorVendaId(conn, id)) {
                    conn.rollback();
                    return false;
                }

                ps.setInt(1, id);
                int linhasAfetadas = ps.executeUpdate();
                if (linhasAfetadas <= 0) {
                    conn.rollback();
                    return false;
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro ao excluir venda: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar para excluir venda: " + e.getMessage());
            return false;
        }
    }
}