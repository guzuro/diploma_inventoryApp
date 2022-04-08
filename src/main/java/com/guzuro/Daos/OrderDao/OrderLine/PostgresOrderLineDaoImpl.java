package com.guzuro.Daos.OrderDao.OrderLine;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresOrderLineDaoImpl implements OrderLineDao{

    SqlClient pgClient;

    public PostgresOrderLineDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<OrderLine>> addOrderLines(CopyOnWriteArrayList<OrderLine> orderLines) {
        return null;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<OrderLine>> getOrderLines(int order_id) {
        return null;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<OrderLine>> updateOrderLine(OrderLine orderLine) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteOrderLine(int orderLine_id) {
        return null;
    }
}
