package com.guzuro.Daos.OrderDao.OrderLine;

import com.guzuro.Daos.ProductDao.Product;

public class OrderLine {
    private int id;
    private Product product;
    private int quantity;
    private double line_total;


    public OrderLine() {
    }

    public OrderLine(Product product, int quantity, double line_total) {
        this.product = product;
        this.quantity = quantity;
        this.line_total = line_total;
    }

    public OrderLine(int id, Product product, int quantity, double line_total) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.line_total = line_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLine_total() {
        return line_total;
    }

    public void setLine_total(double line_total) {
        this.line_total = line_total;
    }
}
