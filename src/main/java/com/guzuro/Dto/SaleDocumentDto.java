package com.guzuro.Dto;

public class SaleDocumentDto {
    private int id;
    private String created_at;
    private boolean is_payed;
    private double total;
    private String client_name;

    public SaleDocumentDto() {}

    public SaleDocumentDto(int id, String created_at, boolean is_payed, double total, String client_name) {
        this.id = id;
        this.created_at = created_at;
        this.is_payed = is_payed;
        this.total = total;
        this.client_name = client_name;
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

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
