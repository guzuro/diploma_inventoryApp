package com.guzuro.Daos.ProductDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Models.Roles.Employee;
import io.vertx.core.Vertx;
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
                "unit, photos, warehouse_id, company_id " +
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
    public CompletableFuture<CopyOnWriteArrayList<Product>> removeProduct(double sku) {
        return null;
    }

    @Override
    public CompletableFuture<Product> addProduct(Product product) {
        return null;
    }

    @Override
    public CompletableFuture<Product> updateProduct(Product product) {
        return null;
    }
}
