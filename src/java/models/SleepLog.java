package models;

import java.sql.Timestamp;

public class SleepLog {
    private int id;
    private Integer userId;
    private Integer dailyLogId;
    private Timestamp bedTime;
    private Timestamp wakeTime;
    private double totalHours;
    private int qualityPercentage;

    public SleepLog() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getDailyLogId() { return dailyLogId; }
    public void setDailyLogId(Integer dailyLogId) { this.dailyLogId = dailyLogId; }

    public Timestamp getBedTime() { return bedTime; }
    public void setBedTime(Timestamp bedTime) { this.bedTime = bedTime; }

    public Timestamp getWakeTime() { return wakeTime; }
    public void setWakeTime(Timestamp wakeTime) { this.wakeTime = wakeTime; }

    public double getTotalHours() { return totalHours; }
    public void setTotalHours(double totalHours) { this.totalHours = totalHours; }

    public int getQualityPercentage() { return qualityPercentage; }
    public void setQualityPercentage(int qualityPercentage) { this.qualityPercentage = qualityPercentage; }
}
