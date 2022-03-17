package com.guzuro.Daos.Config.Category;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresCategoryDaoImpl implements CategoryDao {
    SqlClient pgClient;

    public PostgresCategoryDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<Category> addCategory(Category category, int company_id) {
        CompletableFuture<Category> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("INSERT INTO db_category(name, company_id)" +
                "VALUES ($1, $2) RETURNING id, name;").execute(
                Tuple.of(category.getName(), company_id),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Category.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                }
        );
        return future;
    }

    @Override
    public CompletableFuture<Category> updateCategory(Category category) {
        CompletableFuture<Category> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("UPDATE db_category " +
                "SET name = $1 WHERE id = $2 RETURNING id, name;").execute(
                Tuple.of(category.getName(), category.getId()),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Category.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                }
        );
        return future;
    }

    @Override
    public CompletableFuture<Boolean> deleteCategory(int sale_id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        this.pgClient.preparedQuery("DELETE FROM db_category WHERE id = $1;")
                .execute(Tuple.of(sale_id), ar -> {
                    if (ar.succeeded()) {
                        future.complete(true);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Category>> getCategories(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<Category>> future = new CompletableFuture<>();
        this.pgClient.preparedQuery("SELECT id, name FROM db_category WHERE company_id = $1;")
                .execute(Tuple.of(company_id), ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<Category> salesList = new CopyOnWriteArrayList<>();

                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> salesList.add(row.toJson().mapTo(Category.class)));
                        }

                        future.complete(salesList);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }
}



