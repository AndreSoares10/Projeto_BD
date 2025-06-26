package br.inatel.dao;

import java.sql.*;

public class UsuarioDAO extends ConnectionDAO {

    public UsuarioDAO() {
        connect();
    }

    private boolean perfilExiste(int idPerfil) {
        String sql = "SELECT 1 FROM perfil WHERE ID_perfil = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idPerfil);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar perfil: " + e.getMessage());
            return false;
        }
    }

    public boolean inserirUsuario(int id, String nome, String email, int idPerfil) {
        if (!perfilExiste(idPerfil)) {
            System.out.println("Erro: Perfil com ID " + idPerfil + " não existe. Usuário não inserido.");
            return false;
        }
        try (CallableStatement cs = con.prepareCall("{call InserirUsuario(?, ?, ?, ?)}")) {
            cs.setInt(1, id);
            cs.setString(2, nome);
            cs.setString(3, email);
            cs.setInt(4, idPerfil);
            cs.execute();
            System.out.println("Usuário inserido com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarUsuario(int id, String nome, String email, int idPerfil) {
        if (!perfilExiste(idPerfil)) {
            System.out.println("Erro: Perfil com ID " + idPerfil + " não existe. Usuário não atualizado.");
            return false;
        }
        try (CallableStatement cs = con.prepareCall("{call AtualizarUsuario(?, ?, ?, ?)}")) {
            cs.setInt(1, id);
            cs.setString(2, nome);
            cs.setString(3, email);
            cs.setInt(4, idPerfil);
            cs.execute();
            System.out.println("Usuário atualizado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarUsuario(int idUsuario) {
        try (CallableStatement cs = con.prepareCall("{call DeletarUsuario(?)}")) {
            cs.setInt(1, idUsuario);
            cs.execute();
            System.out.println("Usuário deletado com sucesso.");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
            return false;
        }
    }

    public void listarUsuarios() {
        String sql = "SELECT * FROM usuario";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Lista de usuários:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID_usuario") +
                        " | Nome: " + rs.getString("nome") +
                        " | Email: " + rs.getString("email") +
                        " | ID Perfil: " + rs.getInt("ID_perfil"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    public void buscarUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE ID_usuario = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID_usuario") +
                            " | Nome: " + rs.getString("nome") +
                            " | Email: " + rs.getString("email") +
                            " | ID Perfil: " + rs.getInt("ID_perfil"));
                } else {
                    System.out.println("Usuário não encontrado.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
    }
}
