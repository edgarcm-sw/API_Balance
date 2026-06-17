package models;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private String password;
    private String alias;
    private String avatarUrl;
    private Timestamp createdAt;

    // Datos de perfil (se usan solo en registro, no se persisten en esta clase)
    private Integer age;
    private Double weight;
    private Double height;
    private Double tmb;
    private Double getd;

    public User() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Double getTmb() { return tmb; }
    public void setTmb(Double tmb) { this.tmb = tmb; }

    public Double getGetd() { return getd; }
    public void setGetd(Double getd) { this.getd = getd; }
}