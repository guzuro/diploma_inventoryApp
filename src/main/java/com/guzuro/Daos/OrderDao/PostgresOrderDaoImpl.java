package com.guzuro.Daos.OrderDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresOrderDaoImpl implements OrderDao {
    SqlClient pgClient;

    public PostgresOrderDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Order>> getOrders(int company_id) {
        return null;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Order>> getOrdersBySupplier(int supplier_id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteOrder(int order_id) {
        return null;
    }

    @Override
    public CompletableFuture<Order> updateOrder(Order order) {
        return null;
    }

    @Override
    public CompletableFuture<Order> addOrder(Order order) {
        return null;
    }

    @Override
    public CompletableFuture<Order> getOrder(int order_id) {
        return null;
    }
}



