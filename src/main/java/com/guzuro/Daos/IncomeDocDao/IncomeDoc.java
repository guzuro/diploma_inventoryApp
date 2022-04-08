package com.guzuro.Daos.IncomeDocDao;

import com.guzuro.Daos.OrderDao.Order;
import com.guzuro.Daos.SupplierDao.Supplier;

public class IncomeDoc {

    private int id;
    private Supplier supplier;
    private Order order;
    private int company_id;
    private String created_at;

    public IncomeDoc() {
    }

    public IncomeDoc(int id, Supplier supplier, Order order, int company_id, String created_at) {
        this.id = id;
        this.supplier = supplier;
        this.order = order;
        this.company_id = company_id;
        this.created_at = created_at;
    }

    public IncomeDoc(Supplier supplier, Order order, int company_id, String created_at) {
        this.supplier = supplier;
        this.order = order;
        this.company_id = company_id;
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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}