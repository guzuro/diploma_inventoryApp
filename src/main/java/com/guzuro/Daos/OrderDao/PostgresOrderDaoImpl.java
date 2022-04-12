package com.guzuro.Daos.OrderDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.OrderDao.OrderLine.OrderLine;
import com.guzuro.Daos.OrderDao.OrderLine.OrderLineDao;
import com.guzuro.Daos.OrderDao.OrderLine.PostgresOrderLineDaoImpl;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

public class PostgresOrderDaoImpl implements OrderDao {
    SqlClient pgClient;
    OrderLineDao orderLineDao;

    public PostgresOrderDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
        this.orderLineDao = new PostgresOrderLineDaoImpl(vertx);
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Order>> getOrders(int incomeDoc_id) {
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
        CompletableFuture<Order> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "INSERT INTO db_order (total) " +
                "VALUES ($1) " +
                "RETURNING order_id, total;")
                .execute(Tuple.of(order.getTotal()), ar -> {
                    if (ar.succeeded()) {
                        Order resOrder = ar.result().iterator().next().toJson().mapTo(Order.class);

                        CopyOnWriteArrayList<CompletableFuture<OrderLine>> list = new CopyOnWriteArrayList<>();
                        CopyOnWriteArrayList<OrderLine> lines = new CopyOnWriteArrayList<>();

                        order.getOrderLines().forEach(orderLine -> {
                            list.add(this.orderLineDao.addOrderLines(orderLine, order.getOrder_id()));
                        });

                        CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]))
                                .thenAccept(aVoid -> {
                                    list.forEach(orderLineCompletableFuture -> {
                                        try {
                                            lines.add(orderLineCompletableFuture.get());
                                        } catch (InterruptedException | ExecutionException e) {
                                            future.completeExceptionally(e.getCause());
                                        }
                                    });
                                    resOrder.setOrderLines(lines);
                                    future.complete(resOrder);
                                });
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<Order> getOrder(int order_id) {

        CompletableFuture<Order> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "SELECT order_id, total FROM db_order WHERE order_id = $1;")
                .execute(Tuple.of(order_id),
                        ar -> {
                            if (ar.succeeded()) {
                                JsonObject jsonObject = ar.result().iterator().next().toJson();

                                Order order = new Order();
                                order.setOrder_id(jsonObject.getInteger("order_id"));
                                order.setTotal(jsonObject.getDouble("total"));

                                this.orderLineDao.getOrderLines(order.getOrder_id())
                                        .thenAccept(orderLines -> {
                                            order.setOrderLines(orderLines);
                                            future.complete(order);
                                        }).exceptionally(throwable -> {
                                    future.completeExceptionally(throwable);
                                    return null;
                                });
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });

        return future;

    }
}



