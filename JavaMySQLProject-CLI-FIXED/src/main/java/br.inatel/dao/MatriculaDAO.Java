package br.inatel.dao;

import java.sql.*;

public class MatriculaDAO extends ConnectionDAO {

    public MatriculaDAO() {
        connect();
    }

    public boolean inserirAluno(String nome, int idPerfil) {
        try (CallableStatement cs = con.prepareCall("{call InserirAluno(?, ?)}")) {
            cs.setString(1, nome);
            cs.setInt(2, idPerfil);
            cs.execute();
            System.out.println("Aluno inserido com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarAluno(int idMatricula, String nome, int idPerfil) {
        try (CallableStatement cs = con.prepareCall("{call AtualizarAluno(?, ?, ?)}")) {
            cs.setInt(1, idMatricula);
            cs.setString(2, nome);
            cs.setInt(3, idPerfil);
            cs.execute();
            System.out.println("Aluno atualizado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarAluno(int idMatricula) {
        try (CallableStatement cs = con.prepareCall("{call DeletarAluno(?)}")) {
            cs.setInt(1, idMatricula);
            cs.execute();
            System.out.println("Aluno deletado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar aluno: " + e.getMessage());
            return false;
        }
    }

    public void listarAlunos() {
        String sql = "SELECT * FROM matricula";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Lista de Alunos:");
            while (rs.next()) {
                System.out.println("ID Matrícula: " + rs.getInt("ID_matricula") +
                        " | Nome: " + rs.getString("aluno") +
                        " | ID Perfil: " + rs.getInt("ID_perfil"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }
    }

    public void listarAlunosComCursos() {
        String sql = "SELECT * FROM AlunosComCursos";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Alunos com cursos:");
            while (rs.next()) {
                System.out.println("ID Matrícula: " + rs.getInt("ID_matricula"));
                System.out.println("Aluno: " + rs.getString("aluno"));
                System.out.println("Perfil: " + rs.getString("perfil_info"));
                System.out.println("Curso: " + rs.getString("curso_nome"));
                System.out.println("Disponível: " + rs.getBoolean("curso_disponivel"));
                System.out.println("-------------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar alunos com cursos: " + e.getMessage());
        }
    }
}
