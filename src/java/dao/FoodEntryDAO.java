package dao;

import db.DatabaseConnection;
import models.FoodEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodEntryDAO {

    public FoodEntry createFoodEntry(FoodEntry entry) {
        String sql = "INSERT INTO Food_Entry (daily_log_id, food_item_id, meal_category_id, quantity, total_calories, entry_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entry.getDailyLogId());
            stmt.setInt(2, entry.getFoodItemId());
            stmt.setInt(3, entry.getMealCategoryId());
            stmt.setDouble(4, entry.getQuantity());
            stmt.setDouble(5, entry.getTotalCalories());
            stmt.setTime(6, entry.getEntryTime());

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

    public List<FoodEntry> getFoodEntriesByUserId(int userId) {
        List<FoodEntry> entries = new ArrayList<>();
        String sql = "SELECT fe.id, fe.daily_log_id, fe.food_item_id, fe.meal_category_id, " +
                     "fe.quantity, fe.total_calories, fe.entry_time, fe.created_at " +
                     "FROM Food_Entry fe " +
                     "JOIN Daily_Log dl ON fe.daily_log_id = dl.id " +
                     "WHERE dl.user_id = ? " +
                     "ORDER BY dl.log_date DESC, fe.entry_time DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FoodEntry entry = new FoodEntry();
                    entry.setId(rs.getInt("id"));
                    entry.setDailyLogId(rs.getInt("daily_log_id"));
                    entry.setFoodItemId(rs.getInt("food_item_id"));
                    entry.setMealCategoryId(rs.getInt("meal_category_id"));
                    entry.setQuantity(rs.getDouble("quantity"));
                    entry.setTotalCalories(rs.getDouble("total_calories"));
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

    public boolean deleteFoodEntry(int id) {
        String sql = "DELETE FROM Food_Entry WHERE id = ?";
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
