package models;

import java.sql.Time;
import java.security.Timestamp;

public class FoodEntry {
    private int id;
    private Integer dailyLogId;
    private int foodItemId;
    private int mealCategoryId;
    private double quantity;
    private double totalCalories;
    private Time entryTime;
    private Timestamp createdAt;

    public FoodEntry() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getDailyLogId() { return dailyLogId; }
    public void setDailyLogId(Integer dailyLogId) { this.dailyLogId = dailyLogId; }

    public int getFoodItemId() { return foodItemId; }
    public void setFoodItemId(int foodItemId) { this.foodItemId = foodItemId; }

    public int getMealCategoryId() { return mealCategoryId; }
    public void setMealCategoryId(int mealCategoryId) { this.mealCategoryId = mealCategoryId; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(double totalCalories) { this.totalCalories = totalCalories; }

    public Time getEntryTime() { return entryTime; }
    public void setEntryTime(Time entryTime) { this.entryTime = entryTime; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
