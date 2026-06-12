package models;

public class ActivityType {
    private int id;
    private String name;
    private double caloriesPerMinute;

    public ActivityType() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCaloriesPerMinute() { return caloriesPerMinute; }
    public void setCaloriesPerMinute(double caloriesPerMinute) { this.caloriesPerMinute = caloriesPerMinute; }
}
