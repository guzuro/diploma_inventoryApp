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

//        SELECT B.id AS book_id, B.name AS book_name,
//        A.name AS author_name ,A.ID AS author_id
//        FROM book B
//        LEFT JOIN author A ON B.id = A.book_id
//        ;

        this.pgClient.preparedQuery("SELECT " +
                "db_product.sku, db_product.category, db_product.name, db_product.description, " +
                "db_product.price_base, db_product.price_sale, db_product.currency, db_product.quantity, " +
                "db_product.unit, db_product.photo_main, db_product.warehouse_id, db_product.company_id, " +
                "db_product_photo.photos" +
                " FROM db_product " +
                "LEFT JOIN db_product_photo " +
                "ON db_product_photo.product_id = db_product.sku " +
                "WHERE db_product.company_id = $1")
                .execute(Tuple.of(company_id), ar -> {
                    if (ar.succeeded()) {
                        System.out.println(ar.result().iterator().next().toJson());
                        CopyOnWriteArrayList<Product> productList = new CopyOnWriteArrayList<>();
                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> {
                                productList.add(row.toJson().mapTo(Product.class));
                            });
                            future.complete(productList);
                        } else {
                            future.complete(productList);
                        }
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
