package br.inatel.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDAO {
    public static Connection con;

    public boolean connect() {
        boolean sucesso = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // força carregamento do driver
            String url = "jdbc:mysql://localhost:3306/escola";
            String user = "root";
            String password = "123456";
            con = DriverManager.getConnection(url, user, password);
            sucesso = true;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return sucesso;
    }
}
