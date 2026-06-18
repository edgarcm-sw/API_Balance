package dao;

import db.DatabaseConnection;
import models.SleepLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SleepLogDAO {

    public SleepLog createSleepLog(SleepLog log) {
        String sql = "INSERT INTO Sleep_Log (daily_log_id, bed_time, wake_time, total_hours, quality_percentage) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, log.getDailyLogId());
            stmt.setTimestamp(2, log.getBedTime());
            stmt.setTimestamp(3, log.getWakeTime());
            stmt.setDouble(4, log.getTotalHours());
            stmt.setInt(5, log.getQualityPercentage());

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

    public List<SleepLog> getSleepLogsByUserId(int userId) {
        List<SleepLog> logs = new ArrayList<>();
        String sql = "SELECT sl.id, sl.daily_log_id, sl.bed_time, sl.wake_time, " +
                     "sl.total_hours, sl.quality_percentage, sl.created_at, dl.user_id " +
                     "FROM Sleep_Log sl " +
                     "JOIN Daily_Log dl ON sl.daily_log_id = dl.id " +
                     "WHERE dl.user_id = ? " +
                     "ORDER BY sl.bed_time DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SleepLog log = new SleepLog();
                    log.setId(rs.getInt("id"));
                    log.setUserId(rs.getInt("user_id"));
                    log.setDailyLogId(rs.getInt("daily_log_id"));
                    log.setBedTime(rs.getTimestamp("bed_time"));
                    log.setWakeTime(rs.getTimestamp("wake_time"));
                    log.setTotalHours(rs.getDouble("total_hours"));
                    log.setQualityPercentage(rs.getInt("quality_percentage"));
                    log.setCreatedAt(rs.getTimestamp("created_at"));
                    logs.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public boolean deleteSleepLog(int id) {
        String sql = "DELETE FROM Sleep_Log WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
