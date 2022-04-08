package com.guzuro.Daos.OrderDao;

import com.guzuro.Daos.OrderDao.OrderLine.OrderLine;
import com.thoughtworks.qdox.model.expression.Or;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface OrderDao {

    CompletableFuture<CopyOnWriteArrayList<Order>> getOrders(int incomeDoc_id);

    CompletableFuture<Boolean> deleteOrder(int order_id);

    CompletableFuture<Order> updateOrder(Order order);

    CompletableFuture<Order> addOrder(Order order);

    CompletableFuture<Order> getOrder(int order_id);

}