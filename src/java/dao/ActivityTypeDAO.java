package dao;

import db.DatabaseConnection;
import models.ActivityType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityTypeDAO {

    public List<ActivityType> getAllActivityTypes() {
        List<ActivityType> types = new ArrayList<>();
        String sql = "SELECT * FROM Activity_Type ORDER BY name";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ActivityType type = new ActivityType();
                type.setId(rs.getInt("id"));
                type.setName(rs.getString("name"));
                type.setCaloriesPerMinute(rs.getDouble("calories_per_minute"));
                types.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }
}
