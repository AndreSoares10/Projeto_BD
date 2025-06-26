package br.inatel.dao;

import java.sql.CallableStatement;

public class ProcedureDAO extends ConnectionDAO {

    public ProcedureDAO() {
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
}
