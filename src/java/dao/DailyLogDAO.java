package dao;

import db.DatabaseConnection;
import models.DailyLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DailyLogDAO {

    public DailyLog createDailyLog(DailyLog log) {
        String sql = "INSERT INTO Daily_Log (user_id, log_date, calorie_goal, calories_consumed, calories_remaining) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, log.getUserId());
            stmt.setDate(2, log.getLogDate());
            stmt.setDouble(3, log.getCalorieGoal());
            stmt.setDouble(4, log.getCaloriesConsumed());
            stmt.setDouble(5, log.getCaloriesRemaining());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        log.setId(rs.getInt(1));
                    }
                }
            }
            return log;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DailyLog> getLogsByUserId(int userId) {
        List<DailyLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM Daily_Log WHERE user_id = ? ORDER BY log_date DESC";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DailyLog log = new DailyLog();
                    log.setId(rs.getInt("id"));
                    log.setUserId(rs.getInt("user_id"));
                    log.setLogDate(rs.getDate("log_date"));
                    log.setCalorieGoal(rs.getDouble("calorie_goal"));
                    log.setCaloriesConsumed(rs.getDouble("calories_consumed"));
                    log.setCaloriesRemaining(rs.getDouble("calories_remaining"));
                    log.setCreatedAt(rs.getTimestamp("created_at"));
                    logs.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}
