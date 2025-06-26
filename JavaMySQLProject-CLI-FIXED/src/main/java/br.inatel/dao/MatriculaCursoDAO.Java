package br.inatel.dao;

import java.sql.*;

public class MatriculaCursoDAO extends ConnectionDAO {

    public MatriculaCursoDAO() {
        connect();
    }

    // Vincula um aluno a um curso na tabela matricula_curso
    public boolean vincularAlunoACurso(int idMatricula, int idCurso) {
        String sql = "INSERT INTO matricula_curso (ID_matricula, ID_curso) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);
            ps.setInt(2, idCurso);
            ps.executeUpdate();
            System.out.println("Aluno vinculado ao curso com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao vincular aluno ao curso: " + e.getMessage());
            return false;
        }
    }

    // Lista todos os cursos de um aluno pelo ID da matrícula
    public void listarCursosDoAluno(int idMatricula) {
        String sql = "SELECT c.ID_curso, c.nome, c.cargaHoraria, c.curso_disponivel " +
                "FROM curso c " +
                "JOIN matricula_curso mc ON c.ID_curso = mc.ID_curso " +
                "WHERE mc.ID_matricula = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Cursos do aluno com matrícula ID " + idMatricula + ":");
                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    System.out.println("ID Curso: " + rs.getInt("ID_curso") +
                            " | Nome: " + rs.getString("nome") +
                            " | Carga Horária: " + rs.getInt("cargaHoraria") +
                            " | Disponível: " + rs.getBoolean("curso_disponivel"));
                }
                if (!encontrado) {
                    System.out.println("Nenhum curso encontrado para este aluno.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cursos do aluno: " + e.getMessage());
        }
    }

    // Lista todos os alunos vinculados a um curso pelo ID do curso
    public void listarAlunosDoCurso(int idCurso) {
        String sql = "SELECT m.ID_matricula, m.aluno, m.ID_perfil " +
                "FROM matricula m " +
                "JOIN matricula_curso mc ON m.ID_matricula = mc.ID_matricula " +
                "WHERE mc.ID_curso = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Alunos do curso ID " + idCurso + ":");
                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    System.out.println("ID Matrícula: " + rs.getInt("ID_matricula") +
                            " | Nome: " + rs.getString("aluno") +
                            " | ID Perfil: " + rs.getInt("ID_perfil"));
                }
                if (!encontrado) {
                    System.out.println("Nenhum aluno encontrado para este curso.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar alunos do curso: " + e.getMessage());
        }
    }
}
