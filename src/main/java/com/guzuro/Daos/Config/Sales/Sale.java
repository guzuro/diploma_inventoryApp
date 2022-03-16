package com.guzuro.Daos.Config.Sales;

public class Sale {

    private int id;
    private String name;
    private String type;
    private double value;
    private Boolean isActive;

    public Sale() {
    }

    public Sale(String name, String type, double value, Boolean isActive) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isActive = isActive;
    }

    public Sale(int id, String name, String type, double value, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Boolean getIs_active() {
        return isActive;
    }

    public void setIs_active(Boolean active) {
        isActive = active;
    }
}