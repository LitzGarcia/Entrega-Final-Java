
package org.example.repository;

import org.example.entities.EntityRepository;
import org.example.entities.User;
import org.example.database.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class UserRepository implements EntityRepository<User> {

    private static final String INSERT_SQL = "INSERT INTO users (uuid, name, email, password) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM users WHERE uuid = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM users";
    private static final String DELETE_SQL = "DELETE FROM users WHERE uuid = ?";

    @Override
    public void save(User user) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {

            stmt.setString(1, user.getUuid().toString());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            stmt.executeUpdate();
            System.out.println(" Usuário salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println(" Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar todos os usuários: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void deleteById(UUID uuid) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();
            System.out.println("✅ Usuário deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao deletar usuário: " + e.getMessage());
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String name = rs.getString("name");
                String password = rs.getString("password");
                return Optional.of(new User(uuid, name, email, password));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar usuário por e-mail: " + e.getMessage());
        }
        return Optional.empty();
    }

}
