package com.guzuro.Daos.OrderDao;

import com.guzuro.Daos.OrderDao.OrderLine.OrderLine;

import java.util.concurrent.CopyOnWriteArrayList;

public class Order {

    private int order_id;
    private double total;
    private CopyOnWriteArrayList<OrderLine> orderLines;

    public Order() {
    }

    public Order(double total, CopyOnWriteArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
        this.total = total;
    }

    public Order(int order_id, double total, CopyOnWriteArrayList<OrderLine> orderLines) {
        this.order_id = order_id;
        this.orderLines = orderLines;
        this.total = total;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CopyOnWriteArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(CopyOnWriteArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}