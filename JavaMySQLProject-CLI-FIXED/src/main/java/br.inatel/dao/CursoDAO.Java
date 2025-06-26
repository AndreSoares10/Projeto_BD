package br.inatel.dao;

import java.sql.*;

public class CursoDAO extends ConnectionDAO {

    public CursoDAO() {
        connect();
    }

    public boolean inserirCurso(int idCurso, String nome, int cargaHoraria, boolean disponivel) {
        try (CallableStatement cs = con.prepareCall("{call InserirCurso(?, ?, ?, ?)}")) {
            cs.setInt(1, idCurso);
            cs.setString(2, nome);
            cs.setInt(3, cargaHoraria);
            cs.setBoolean(4, disponivel);
            cs.execute();
            System.out.println("Curso inserido com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir curso: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarCurso(int idCurso, String nome, int cargaHoraria, boolean disponivel) {
        try (CallableStatement cs = con.prepareCall("{call AtualizarCurso(?, ?, ?, ?)}")) {
            cs.setInt(1, idCurso);
            cs.setString(2, nome);
            cs.setInt(3, cargaHoraria);
            cs.setBoolean(4, disponivel);
            cs.execute();
            System.out.println("Curso atualizado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarCurso(int idCurso) {
        try (CallableStatement cs = con.prepareCall("{call DeletarCurso(?)}")) {
            cs.setInt(1, idCurso);
            cs.execute();
            System.out.println("Curso deletado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar curso: " + e.getMessage());
            return false;
        }
    }

    public void listarCursos() {
        String sql = "SELECT * FROM curso";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Lista de Cursos:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID_curso") +
                        " | Nome: " + rs.getString("nome") +
                        " | Carga Horária: " + rs.getInt("cargaHoraria") +
                        " | Disponível: " + rs.getBoolean("curso_disponivel"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    public void buscarCursoPorId(int idCurso) {
        String sql = "SELECT * FROM curso WHERE ID_curso = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCurso);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID_curso") +
                            " | Nome: " + rs.getString("nome") +
                            " | Carga Horária: " + rs.getInt("cargaHoraria") +
                            " | Disponível: " + rs.getBoolean("curso_disponivel"));
                } else {
                    System.out.println("Curso não encontrado.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar curso: " + e.getMessage());
        }
    }
}
