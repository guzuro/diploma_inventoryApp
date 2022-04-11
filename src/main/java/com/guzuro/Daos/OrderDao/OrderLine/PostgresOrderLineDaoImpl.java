package com.guzuro.Daos.OrderDao.OrderLine;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.ProductDao.PostgresProductDaoImpl;
import com.guzuro.Daos.ProductDao.Product;
import com.guzuro.Daos.ProductDao.ProductDao;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresOrderLineDaoImpl implements OrderLineDao {

    SqlClient pgClient;
    ProductDao productDao;

    public PostgresOrderLineDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
        this.productDao = new PostgresProductDaoImpl(vertx);
    }

    @Override
    public CompletableFuture<OrderLine> addOrderLines(OrderLine orderLine, int order_id) {

        CompletableFuture<OrderLine> future = new CompletableFuture<>();
        this.pgClient.preparedQuery("" +
                "INSERT INTO db_order_line(product_id, quantity, line_total, order_id) " +
                "VALUES ($1, $2, $3, $4) " +
                "RETURNING id, quantity, product_id, line_total")
                .execute(Tuple.of(orderLine.getProduct().getSku(), orderLine.getQuantity(), orderLine.getLine_total(), order_id),
                        ar -> {
                            if (ar.succeeded()) {

                                JsonObject resultRow = ar.result().iterator().next().toJson();
                                OrderLine ol = new OrderLine();
                                ol.setId(resultRow.getInteger("id"));
                                ol.setLine_total(resultRow.getDouble("line_total"));
                                ol.setQuantity(resultRow.getDouble("quantity"));

                                this.productDao.getProductBySku(resultRow.getLong("product_id"))
                                        .thenAccept(product -> {
                                            ol.setProduct(product);
                                            future.complete(ol);
                                        })
                                        .exceptionally(exc -> {
                                            System.out.println(exc.getMessage());
                                            return null;
                                        });

                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });
        return future;
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
