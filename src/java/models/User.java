package models;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private int age;
    private double weight;
    private double height;
    private double tmb;
    private double getd;
    private Timestamp createdAt;

    public User() {}

    public User(int id, String name, int age, double weight, double height, double tmb, double getd, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.tmb = tmb;
        this.getd = getd;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

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

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
