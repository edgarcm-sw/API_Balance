package dao;

import db.DatabaseConnection;
import models.User;
import models.UserProfile;
import util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final UserProfileDAO profileDAO = new UserProfileDAO();

    // Registro: crea User + User_Profile en una sola transacción
    public User createUser(User user) {
        String sql = "INSERT INTO User (name, password, alias, avatar_url) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, PasswordUtil.hash(user.getPassword()));
                stmt.setString(3, user.getAlias());
                stmt.setString(4, user.getAvatarUrl());
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) user.setId(rs.getInt(1));
                }
            }

            // Insertar User_Profile en la misma transacción
            String sqlProfile = "INSERT INTO User_Profile (user_id, age, weight, height, tmb, getd) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlProfile)) {
                stmt2.setInt(1, user.getId());
                stmt2.setInt(2, user.getAge() != null ? user.getAge() : 0);
                stmt2.setDouble(3, user.getWeight() != null ? user.getWeight() : 0);
                stmt2.setDouble(4, user.getHeight() != null ? user.getHeight() : 0);
                stmt2.setDouble(5, user.getTmb() != null ? user.getTmb() : 0);
                stmt2.setDouble(6, user.getGetd() != null ? user.getGetd() : 0);
                stmt2.executeUpdate();
            }

            conn.commit();
            return user;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return null;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extractUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) users.add(extractUser(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUser(int id, User user) {
        String sql = "UPDATE User SET alias=?, avatar_url=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getAlias());
            stmt.setString(2, user.getAvatarUrl());
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setAlias(rs.getString("alias"));
        user.setAvatarUrl(rs.getString("avatar_url"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}