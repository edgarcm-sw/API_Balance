package models;

import java.sql.Timestamp;

public class UserProfile {
    private int id;
    private int userId;
    private int age;
    private double weight;
    private double height;
    private double tmb;
    private double getd;
    private Timestamp updatedAt;

    public UserProfile() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getTmb() { return tmb; }
    public void setTmb(double tmb) { this.tmb = tmb; }

    public double getGetd() { return getd; }
    public void setGetd(double getd) { this.getd = getd; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}