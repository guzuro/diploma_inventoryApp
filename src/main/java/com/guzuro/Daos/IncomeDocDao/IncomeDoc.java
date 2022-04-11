package com.guzuro.Daos.IncomeDocDao;

import com.guzuro.Daos.OrderDao.Order;
import com.guzuro.Daos.SupplierDao.Supplier;

public class IncomeDoc {

    private int id;
    private Supplier supplier;
    private Order order;
    private String created_at;
    private Boolean is_payed;

    public IncomeDoc() {
    }

    public IncomeDoc(int id, Boolean is_payed) {
        this.id = id;
        this.is_payed = is_payed;
    }

    public IncomeDoc(int id, Supplier supplier, Order order, String created_at, Boolean is_payed) {
        this.id = id;
        this.supplier = supplier;
        this.order = order;
        this.created_at = created_at;
        this.is_payed = is_payed;
    }

    public IncomeDoc(Supplier supplier, Order order, String created_at) {
        this.supplier = supplier;
        this.order = order;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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