package com.guzuro.Daos.SupplierDao;

public class Supplier {

    private int id;
    private String name;
    private String phone;
    private String address;
    private long inn;

    public Supplier() {
    }

    public Supplier(String name, String phone, String address, long inn, int company_id) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.inn = inn;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getInn() {
        return inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }
}