package com.guzuro.Daos.Config.Sales;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.WarehouseDao.Warehouse;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresSaleDaoImpl implements SaleDao {
    SqlClient pgClient;

    public PostgresSaleDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<Sale> addSale(Sale sale, int company_id) {
        CompletableFuture<Sale> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("INSERT INTO db_sales(name, type, value, is_active, company_id)" +
                "VALUES ($1, $2, $3, $4, $5) RETURNING id, name, type, is_active, value;").execute(
                Tuple.of(sale.getName(), sale.getType(), sale.getValue(), sale.getIs_active(), company_id),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Sale.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                }
        );
        return future;
    }

    @Override
    public CompletableFuture<Sale> updateSale(Sale sale) {
        CompletableFuture<Sale> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("UPDATE db_sales " +
                "SET name = $1, type= $2, is_active = $3, value = $4 " +
                "WHERE id = $5 " +
                "RETURNING id, name, type, is_active, value").execute(
                Tuple.of(sale.getName(), sale.getType(), sale.getIs_active(), sale.getValue(), sale.getId()),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Sale.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                }
        );
        return future;
    }

    @Override
    public CompletableFuture<Boolean> deleteSale(int sale_id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        this.pgClient.preparedQuery("DELETE FROM db_sales WHERE id = $1;")
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
    public CompletableFuture<CopyOnWriteArrayList<Sale>> getSales(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<Sale>> future = new CompletableFuture<>();
        this.pgClient.preparedQuery("SELECT id, name, type, value, is_active FROM db_sales WHERE company_id = $1;")
                .execute(Tuple.of(company_id), ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<Sale> salesList = new CopyOnWriteArrayList<>();

                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> salesList.add(row.toJson().mapTo(Sale.class)));
                        }

                        future.complete(salesList);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }
}



