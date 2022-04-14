package com.guzuro.Daos.ProductDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Models.Roles.Employee;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresProductDaoImpl implements ProductDao {
    SqlClient pgClient;

    public PostgresProductDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Product>> getAllProducts(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<Product>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("SELECT " +
                "sku, category, db_product.name, description, " +
                "price_base, price_sale, currency, quantity, " +
                "unit, photos, warehouse_id, company_id, sale_id, sale_value " +
                "FROM db_product " +
                "WHERE company_id = $1")
                .execute(Tuple.of(company_id), ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<Product> productList = new CopyOnWriteArrayList<>();
                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> {
                                productList.add(row.toJson().mapTo(Product.class));
                            });
                        }
                        future.complete(productList);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Product>> getWarehouseProducts(int warehouse_id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> removeProduct(long sku) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("DELETE FROM db_product WHERE sku = $1")
                .execute(Tuple.of(sku), ar -> {
                    if (ar.succeeded()) {
                        future.complete(true);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;

    }

    @Override
    public CompletableFuture<Product> addProduct(Product product) {
        CompletableFuture<Product> future = new CompletableFuture<>();
        String[] p = new String[product.getPhotos().size()];
        product.getPhotos().toArray(p);
        this.pgClient.preparedQuery("INSERT INTO db_product" +
                "(sku, category, name, description, " +
                "price_base, price_sale, currency, quantity, " +
                "unit, photos, warehouse_id, company_id, sale_id, sale_value) " +
                "VALUES (" +
                "$1, $2, $3, $4, $5, $6, $7, " +
                "$8, $9, $10, $11, $12, $13, $14" +
                ") " +
                "RETURNING sku, category, name, description," +
                "price_base, price_sale, currency, quantity, " +
                "unit, photos, warehouse_id, company_id, sale_id, sale_value")
                .execute(Tuple.of(
                        product.getSku(), product.getCategory(), product.getName(),
                        product.getDescription(), product.getPrice_base(), product.getPrice_sale(),
                        product.getCurrency(), product.getQuantity(), product.getUnit(),
                        p, product.getWarehouse_id(), product.getCompany_id(),
                        product.getSale_id(), product.getSale_value()
                ), ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Product.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });

        return future;
    }

    @Override
    public CompletableFuture<Product> updateProduct(Product product) {
        CompletableFuture<Product> future = new CompletableFuture<>();

        String[] p = new String[product.getPhotos().size()];
        product.getPhotos().toArray(p);
        this.pgClient.preparedQuery("" +
                "UPDATE db_product " +
                "SET category = $2, name= $3, description= $4, " +
                "price_base= $5, price_sale= $6, currency= $7, quantity= $8, " +
                "unit= $9, photos= $10, warehouse_id= $11, sale_id= $12, sale_value= $13 " +
                "WHERE sku = $1 RETURNING sku, category, name, description," +
                "price_base, price_sale, currency, quantity, " +
                "unit, photos, warehouse_id, company_id, sale_id, sale_value;")
                .execute(Tuple.of(
                        product.getSku(), product.getCategory(), product.getName(),
                        product.getDescription(), product.getPrice_base(), product.getPrice_sale(),
                        product.getCurrency(), product.getQuantity(), product.getUnit(),
                        p, product.getWarehouse_id(), product.getSale_id(), product.getSale_value()
                ), ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Product.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });

        return future;
    }

    @Override
    public CompletableFuture<Product> getProductBySku(long sku) {
        CompletableFuture<Product> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("SELECT " +
                "sku, category, db_product.name, description, " +
                "price_base, price_sale, currency, quantity, " +
                "unit, photos, warehouse_id, company_id, sale_id, sale_value " +
                "FROM db_product " +
                "WHERE sku = $1")
                .execute(Tuple.of(sku), ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Product.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<Boolean> incrementProductQuantity(long sku, int value) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "UPDATE db_product " +
                "SET quantity = quantity + $2 " +
                "WHERE sku = $1;")
                .execute(Tuple.of(
                        sku, value
                ), ar -> {
                    if (ar.succeeded()) {
                        future.complete(true);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> decrementProductQuantity(long sku, int value) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "UPDATE db_product " +
                "SET quantity = quantity - $2  " +
                "WHERE sku = $1;")
                .execute(Tuple.of(
                        sku, value
                ), ar -> {
                    if (ar.succeeded()) {
                        future.complete(true);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });

        return future;
    }


}
