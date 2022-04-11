package com.guzuro.Daos.OrderDao.OrderLine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface OrderLineDao {
    CompletableFuture<OrderLine> addOrderLines(OrderLine orderLine, int order_id);

    CompletableFuture<CopyOnWriteArrayList<OrderLine>> getOrderLines(int order_id);

    CompletableFuture<CopyOnWriteArrayList<OrderLine>> updateOrderLine(OrderLine orderLine);

    CompletableFuture<Boolean> deleteOrderLine(int orderLine_id);
}
