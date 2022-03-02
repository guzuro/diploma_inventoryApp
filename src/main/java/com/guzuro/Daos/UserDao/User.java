package com.guzuro.Daos.UserDao;

public class User {
    private int    id;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String phone;
    private String company;
    private String role;

    public User() {
    }

    public User(int id, String role) {
        this.id = id;
        this.role = role;
    }
    public User(String email, String first_name, String last_name, String phone, String company, String role) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.company = company;
        this.role = role;
    }
    public User(int id, String email, String first_name, String last_name, String phone, String company, String role) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.company = company;
        this.role = role;
    }
    public User(String email, String password, String first_name, String last_name, String phone, String company, String role) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.company = company;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
