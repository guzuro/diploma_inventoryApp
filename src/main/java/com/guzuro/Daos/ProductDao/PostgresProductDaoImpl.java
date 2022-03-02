package com.guzuro.Daos.ProductDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresProductDaoImpl implements ProductDao {
    SqlClient pgClient;

    public PostgresProductDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Product>> getAllProducts() {
        return null;
    }

    @Override
    public CompletableFuture<Product> createProduct(Product product) {
        return null;
    }

    @Override
    public CompletableFuture<Product> updateProduct(Product product) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteProduct(Number productId) {
        return null;
    }
}
