package models;

import java.sql.Time;

public class ExerciseEntry {
    private int id;
    private int dailyLogId;
    private int activityTypeId;
    private int durationMinutes;
    private double caloriesBurned;
    private Time entryTime;

    public ExerciseEntry() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDailyLogId() { return dailyLogId; }
    public void setDailyLogId(int dailyLogId) { this.dailyLogId = dailyLogId; }

    public int getActivityTypeId() { return activityTypeId; }
    public void setActivityTypeId(int activityTypeId) { this.activityTypeId = activityTypeId; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public Time getEntryTime() { return entryTime; }
    public void setEntryTime(Time entryTime) { this.entryTime = entryTime; }
}
