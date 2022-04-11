package com.guzuro.Dto;

public class IncomeDocumentDto {
    private int id;
    private String created_at;
    private int company_id;
    private boolean is_payed;
    private double total;
    private String name;

    public IncomeDocumentDto(int id, String created_at, int company_id, boolean is_payed, double total, String name) {
        this.id = id;
        this.created_at = created_at;
        this.company_id = company_id;
        this.is_payed = is_payed;
        this.total = total;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public boolean isIs_payed() {
        return is_payed;
    }

    public void setIs_payed(boolean is_payed) {
        this.is_payed = is_payed;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
