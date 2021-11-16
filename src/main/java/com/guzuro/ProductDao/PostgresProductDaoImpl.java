package com.guzuro.ProductDao;

import com.guzuro.DaoFactory.PostgresDAOFactory;
import com.guzuro.ProductDao.Product;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
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
