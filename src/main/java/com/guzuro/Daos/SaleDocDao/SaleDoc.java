package com.guzuro.Daos.SaleDocDao;

import com.guzuro.Daos.OrderDao.Order;
import com.guzuro.Daos.SupplierDao.Supplier;

public class SaleDoc {

    private int id;
    private String client_name;
    private Order order;
    private String created_at;
    private Boolean is_payed;

    public SaleDoc() {
    }

    public SaleDoc(int id, Boolean is_payed) {
        this.id = id;
        this.is_payed = is_payed;
    }

    public SaleDoc(int id, String client_name, Order order, String created_at, Boolean is_payed) {
        this.id = id;
        this.client_name = client_name;
        this.order = order;
        this.created_at = created_at;
        this.is_payed = is_payed;
    }

    public SaleDoc(String client_name, Order order, String created_at) {
        this.client_name = client_name;
        this.order = order;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Boolean getIs_payed() {
        return is_payed;
    }

    public void setIs_payed(Boolean is_payed) {
        this.is_payed = is_payed;
    }
}