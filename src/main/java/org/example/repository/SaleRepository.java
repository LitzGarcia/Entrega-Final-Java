package org.example.repository;

import org.example.database.Conexao;
import org.example.entities.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleRepository {
    public void save(Sale sale) {
        String insertSale = "INSERT INTO sales (uuid, user_id, total, payment_method, created_at) VALUES (?, ?, ?, ?, datetime('now'))";
        String insertProductSale = "INSERT INTO sales_products (sale_id, product_id) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar()) {
            PreparedStatement stmtSale = conn.prepareStatement(insertSale);
            stmtSale.setString(1, sale.getUuid().toString());
            stmtSale.setString(2, sale.getUserId().toString());
            stmtSale.setDouble(3, sale.getTotal());
            stmtSale.setString(4, sale.getPaymentMethod());
            stmtSale.executeUpdate();

            for (var productId : sale.getProductIds()) {
                PreparedStatement stmtItem = conn.prepareStatement(insertProductSale);
                stmtItem.setString(1, sale.getUuid().toString());
                stmtItem.setString(2, productId.toString());
                stmtItem.executeUpdate();
            }

            System.out.println("✅ Venda registrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao registrar venda: " + e.getMessage());
        }
    }
}

