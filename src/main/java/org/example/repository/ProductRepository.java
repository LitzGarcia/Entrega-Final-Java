package org.example.repository;

import org.example.database.Conexao;
import org.example.entities.Product;

import java.sql.*;
import java.util.*;

public class ProductRepository {

    public void save(Product product) {
        String sql = "INSERT INTO products (uuid, name, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getUuid().toString());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar produto: " + e.getMessage());
        }
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                products.add(new Product(uuid, name, price, quantity));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar produtos: " + e.getMessage());
        }
        return products;
    }

    public Optional<Product> findById(UUID uuid) {
        String sql = "SELECT * FROM products WHERE uuid = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                return Optional.of(new Product(uuid, name, price, quantity));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar produto por ID: " + e.getMessage());
        }
        return Optional.empty();
    }
}

