package dao;

import db.DatabaseConnection;
import models.ExerciseEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseEntryDAO {

    public ExerciseEntry createExerciseEntry(ExerciseEntry entry) {
        String sql = "INSERT INTO Exercise_Entry (daily_log_id, activity_type_id, duration_minutes, calories_burned, entry_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entry.getDailyLogId());
            stmt.setInt(2, entry.getActivityTypeId());
            stmt.setInt(3, entry.getDurationMinutes());
            stmt.setDouble(4, entry.getCaloriesBurned());
            stmt.setTime(5, entry.getEntryTime());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        entry.setId(rs.getInt(1));
                    }
                }
            }
            return entry;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ExerciseEntry> getExerciseEntriesByUserId(int userId) {
        List<ExerciseEntry> entries = new ArrayList<>();
        String sql = "SELECT ee.id, ee.daily_log_id, ee.activity_type_id, " +
                     "ee.duration_minutes, ee.calories_burned, ee.entry_time " +
                     "FROM Exercise_Entry ee " +
                     "JOIN Daily_Log dl ON ee.daily_log_id = dl.id " +
                     "WHERE dl.user_id = ? " +
                     "ORDER BY dl.log_date DESC, ee.entry_time DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ExerciseEntry entry = new ExerciseEntry();
                    entry.setId(rs.getInt("id"));
                    entry.setDailyLogId(rs.getInt("daily_log_id"));
                    entry.setActivityTypeId(rs.getInt("activity_type_id"));
                    entry.setDurationMinutes(rs.getInt("duration_minutes"));
                    entry.setCaloriesBurned(rs.getDouble("calories_burned"));
                    entry.setEntryTime(rs.getTime("entry_time"));
                    entry.setCreatedAt(rs.getTimestamp("created_at"));
                    entries.add(entry);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public boolean deleteExerciseEntry(int id) {
        String sql = "DELETE FROM Exercise_Entry WHERE id = ?";
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
