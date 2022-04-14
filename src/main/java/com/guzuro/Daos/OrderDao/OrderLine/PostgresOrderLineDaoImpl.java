package com.guzuro.Daos.OrderDao.OrderLine;

import com.guzuro.Daos.Config.Category.Category;
import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.OrderDao.Order;
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
                                ol.setQuantity(resultRow.getInteger("quantity"));

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
        CompletableFuture<CopyOnWriteArrayList<OrderLine>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery(
                "SELECT db_order_line.id, db_order_line.quantity as line_quantity, db_order_line.line_total, db_order_line.product_id, " +
                        "db_product.sku, db_product.category, db_product.name, db_product.description, db_product.price_base, " +
                        "db_product.price_sale, db_product.currency, db_product.quantity as product_quantity," +
                        "db_product.unit, db_product.photos, db_product.warehouse_id, db_product.company_id, db_product.sale_id, db_product.sale_value " +
                        "FROM db_order_line " +
                        "LEFT JOIN db_product " +
                        "ON db_product.sku = db_order_line.product_id " +
                        "WHERE db_order_line.order_id = $1;")
                .execute(Tuple.of(order_id), ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<OrderLine> orderLines = new CopyOnWriteArrayList<>();

                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> {
                                JsonObject jsonObject = row.toJson();
                                OrderLine orderLine = new OrderLine();

                                orderLine.setId(jsonObject.getInteger("id"));
                                orderLine.setQuantity(jsonObject.getInteger("line_quantity"));
                                orderLine.setLine_total(jsonObject.getDouble("line_total"));

                                Product product = new Product();
                                product.setSku(jsonObject.getLong("sku"));
                                product.setCategory(jsonObject.getInteger("category"));
                                product.setName(jsonObject.getString("name"));
                                product.setDescription(jsonObject.getString("description"));
                                product.setPrice_base(jsonObject.getDouble("price_base"));
                                product.setPrice_sale(jsonObject.getDouble("price_sale"));
                                product.setCurrency(jsonObject.getString("currency"));
                                product.setQuantity(jsonObject.getDouble("product_quantity"));
                                product.setUnit(jsonObject.getString("unit"));
                                product.setPhotos(jsonObject.getJsonArray("photos").getList());
                                product.setWarehouse_id(jsonObject.getInteger("warehouse_id"));
                                product.setCompany_id(jsonObject.getInteger("company_id"));
                                product.setSale_id(jsonObject.getInteger("sale_id"));
                                product.setSale_value(jsonObject.getDouble("sale_value"));

                                orderLine.setProduct(product);

                                orderLines.add(orderLine);
                            });
                        }

                        future.complete(orderLines);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
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
