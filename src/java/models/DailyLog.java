package models;

import java.sql.Date;
import java.sql.Timestamp;

public class DailyLog {
    private int id;
    private int userId;
    private Date logDate;
    private double calorieGoal;
    private double caloriesConsumed;
    private double caloriesRemaining;
    private Timestamp createdAt;

    public DailyLog() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getLogDate() { return logDate; }
    public void setLogDate(Date logDate) { this.logDate = logDate; }

    public double getCalorieGoal() { return calorieGoal; }
    public void setCalorieGoal(double calorieGoal) { this.calorieGoal = calorieGoal; }

    public double getCaloriesConsumed() { return caloriesConsumed; }
    public void setCaloriesConsumed(double caloriesConsumed) { this.caloriesConsumed = caloriesConsumed; }

    public double getCaloriesRemaining() { return caloriesRemaining; }
    public void setCaloriesRemaining(double caloriesRemaining) { this.caloriesRemaining = caloriesRemaining; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
