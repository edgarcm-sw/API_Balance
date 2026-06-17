package dao;

import db.DatabaseConnection;
import models.UserProfile;

import java.sql.*;

public class UserProfileDAO {

    public UserProfile createProfile(UserProfile profile) {
        String sql = "INSERT INTO User_Profile (user_id, age, weight, height, tmb, getd) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, profile.getUserId());
            stmt.setInt(2, profile.getAge());
            stmt.setDouble(3, profile.getWeight());
            stmt.setDouble(4, profile.getHeight());
            stmt.setDouble(5, profile.getTmb());
            stmt.setDouble(6, profile.getGetd());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) profile.setId(rs.getInt(1));
            }
            return profile;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserProfile getProfileByUserId(int userId) {
        String sql = "SELECT * FROM User_Profile WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserProfile p = new UserProfile();
                    p.setId(rs.getInt("id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setAge(rs.getInt("age"));
                    p.setWeight(rs.getDouble("weight"));
                    p.setHeight(rs.getDouble("height"));
                    p.setTmb(rs.getDouble("tmb"));
                    p.setGetd(rs.getDouble("getd"));
                    p.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProfile(int userId, UserProfile profile) {
        String sql = "UPDATE User_Profile SET age=?, weight=?, height=?, tmb=?, getd=? WHERE user_id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, profile.getAge());
            stmt.setDouble(2, profile.getWeight());
            stmt.setDouble(3, profile.getHeight());
            stmt.setDouble(4, profile.getTmb());
            stmt.setDouble(5, profile.getGetd());
            stmt.setInt(6, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}