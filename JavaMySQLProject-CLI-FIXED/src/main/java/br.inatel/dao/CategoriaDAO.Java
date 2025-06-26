package br.inatel.dao;

import java.sql.*;

public class CategoriaDAO extends ConnectionDAO {

    public CategoriaDAO() {
        connect();
    }

    public boolean inserirCategoria(int idCategoria, String nome) {
        try (CallableStatement cs = con.prepareCall("{call InserirCategoria(?, ?)}")) {
            cs.setInt(1, idCategoria);
            cs.setString(2, nome);
            cs.execute();
            System.out.println("Categoria inserida com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarCategoria(int idCategoria, String nome) {
        try (CallableStatement cs = con.prepareCall("{call AtualizarCategoria(?, ?)}")) {
            cs.setInt(1, idCategoria);
            cs.setString(2, nome);
            cs.execute();
            System.out.println("Categoria atualizada com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarCategoria(int idCategoria) {
        try (CallableStatement cs = con.prepareCall("{call DeletarCategoria(?)}")) {
            cs.setInt(1, idCategoria);
            cs.execute();
            System.out.println("Categoria deletada com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar categoria: " + e.getMessage());
            return false;
        }
    }

    public void listarCategorias() {
        String sql = "SELECT * FROM categoria";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Lista de Categorias:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID_categoria") + " | Nome: " + rs.getString("nome"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        }
    }

    public void buscarCategoriaPorId(int idCategoria) {
        String sql = "SELECT * FROM categoria WHERE ID_categoria = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCategoria);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID_categoria") + " | Nome: " + rs.getString("nome"));
                } else {
                    System.out.println("Categoria não encontrada.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    public void listarCategoriasPorCurso(int idCurso) {
        String sql = "SELECT cat.nome FROM cursos_categorias cc JOIN categoria cat ON cc.ID_categoria = cat.ID_categoria WHERE cc.ID_curso = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCurso);
            try (ResultSet rs = pst.executeQuery()) {
                System.out.println("Categorias do curso " + idCurso + ":");
                while (rs.next()) {
                    System.out.println("- " + rs.getString("nome"));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar categorias por curso: " + e.getMessage());
        }
    }
}
