package models;

import java.sql.Timestamp;

public class SleepLog {
    private int id;
    private int dailyLogId;
    private Timestamp bedTime;
    private Timestamp wakeTime;
    private double totalHours;
    private int qualityPercentage;

    public SleepLog() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDailyLogId() { return dailyLogId; }
    public void setDailyLogId(int dailyLogId) { this.dailyLogId = dailyLogId; }

    public Timestamp getBedTime() { return bedTime; }
    public void setBedTime(Timestamp bedTime) { this.bedTime = bedTime; }

    public Timestamp getWakeTime() { return wakeTime; }
    public void setWakeTime(Timestamp wakeTime) { this.wakeTime = wakeTime; }

    public double getTotalHours() { return totalHours; }
    public void setTotalHours(double totalHours) { this.totalHours = totalHours; }

    public int getQualityPercentage() { return qualityPercentage; }
    public void setQualityPercentage(int qualityPercentage) { this.qualityPercentage = qualityPercentage; }
}
