package dao;

import db.DatabaseConnection;
import models.FoodItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodItemDAO {

    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> items = new ArrayList<>();
        String sql = "SELECT * FROM Food_Item ORDER BY name";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FoodItem item = new FoodItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setBaseCalories(rs.getDouble("base_calories"));
                item.setDescription(rs.getString("description"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public FoodItem createFoodItem(FoodItem item) {
        String sql = "INSERT INTO Food_Item (name, base_calories, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getBaseCalories());
            stmt.setString(3, item.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        item.setId(rs.getInt(1));
                    }
                }
            }
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
