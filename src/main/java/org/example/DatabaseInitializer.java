package org.example.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void init(Connection conn) {
        try {
            InputStream input = DatabaseInitializer.class.getClassLoader().getResourceAsStream("scripts/init.sql");
            if (input == null) {
                System.out.println("❌ init.sql não encontrado!");
                return;
            }

            StringBuilder sqlBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlBuilder.append(line).append("\n");
                }
            }

            String sql = sqlBuilder.toString();
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("✅ Tabela 'users' criada (se ainda não existia).");
            }

        } catch (IOException | SQLException e) {
            System.out.println("❌ Erro ao executar init.sql: " + e.getMessage());
        }
    }
}
