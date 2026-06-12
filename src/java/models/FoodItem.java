package models;

public class FoodItem {
    private int id;
    private String name;
    private double baseCalories;
    private String description;

    public FoodItem() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBaseCalories() { return baseCalories; }
    public void setBaseCalories(double baseCalories) { this.baseCalories = baseCalories; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
