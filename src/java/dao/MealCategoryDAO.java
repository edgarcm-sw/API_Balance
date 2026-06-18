package dao;

import db.DatabaseConnection;
import models.MealCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealCategoryDAO {

    public List<MealCategory> getAllMealCategories() {
        List<MealCategory> categories = new ArrayList<>();
        String sql = "SELECT * FROM Meal_Category ORDER BY id";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MealCategory cat = new MealCategory();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("name"));
                categories.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
