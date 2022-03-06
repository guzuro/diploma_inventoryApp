package com.guzuro.Daos.CompanyDao;

public class Company {
    private Number id;
    private String name;
    private long inn;
    private String phone;
    private String email;
    private String country;
    private String currency;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(Number id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(Number id, String name, long inn) {
        this.id = id;
        this.name = name;
        this.inn = inn;
    }

    public Company(Number id, String name, long inn, String phone) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.phone = phone;
    }

    public Company(Number id, String name, long inn, String phone, String email) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.phone = phone;
        this.email = email;
    }

    public Company(Number id, String name, long inn, String phone, String email, String country) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.phone = phone;
        this.email = email;
        this.country = country;
    }

    public Company(Number id, String name, long inn, String phone, String email, String country, String currency) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.currency = currency;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getInn() {
        return inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}