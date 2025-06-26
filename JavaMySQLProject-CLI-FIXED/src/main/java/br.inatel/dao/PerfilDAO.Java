package br.inatel.dao;

import java.sql.*;

public class PerfilDAO extends ConnectionDAO {

    public PerfilDAO() {
        connect();
    }

    public boolean inserirPerfil(int idPerfil, String info) {
        try (CallableStatement cs = con.prepareCall("{call InserirPerfil(?, ?)}")) {
            cs.setInt(1, idPerfil);
            cs.setString(2, info);
            cs.execute();
            System.out.println("Perfil inserido com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir perfil: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPerfil(int idPerfil, String novaInfo) {
        try (CallableStatement cs = con.prepareCall("{call AtualizarPerfil(?, ?)}")) {
            cs.setInt(1, idPerfil);
            cs.setString(2, novaInfo);
            cs.execute();
            System.out.println("Perfil atualizado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar perfil: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPerfil(int idPerfil) {
        try (CallableStatement cs = con.prepareCall("{call DeletarPerfil(?)}")) {
            cs.setInt(1, idPerfil);
            cs.execute();
            System.out.println("Perfil deletado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar perfil: " + e.getMessage());
            return false;
        }
    }

    public void listarPerfis() {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM perfil")) {
            System.out.println("Lista de Perfis:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID_perfil") + " | Info: " + rs.getString("info"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar perfis: " + e.getMessage());
        }
    }

    public void buscarPerfilPorId(int idPerfil) {
        String sql = "SELECT * FROM perfil WHERE ID_perfil = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idPerfil);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID_perfil") + " | Info: " + rs.getString("info"));
                } else {
                    System.out.println("Perfil não encontrado.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar perfil: " + e.getMessage());
        }
    }

    public void listarPerfisComMatricula() {
        String sql = "SELECT p.ID_perfil, p.info, m.aluno FROM perfil p JOIN matricula m ON p.ID_perfil = m.ID_perfil";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Perfis com matrícula:");
            while (rs.next()) {
                System.out.println("Perfil ID: " + rs.getInt("ID_perfil") + " | Info: " + rs.getString("info") +
                        " | Aluno: " + rs.getString("aluno"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar perfis com matrícula: " + e.getMessage());
        }
    }
}
