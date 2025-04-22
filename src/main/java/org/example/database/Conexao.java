package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:sqlite:ecommerce.db"; // arquivo ficará na raiz do projeto

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            //System.out.println("✅ Conexão com SQLite estabelecida!");
            return conn;
        } catch (SQLException e) {
            //System.out.println("❌ Erro ao conectar com o banco de dados: " + e.getMessage());
            return null;
        }
    }
}
