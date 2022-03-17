package com.guzuro.Daos.WarehouseDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresWarehouseDaoImpl implements WarehouseDao {
    SqlClient pgClient;

    public PostgresWarehouseDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<Warehouse> addWarehouse(Warehouse warehouse, int company_id) {
        CompletableFuture<Warehouse> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("INSERT INTO db_warehouse(title, address, company_id) " +
                "VALUES ($1, $2, $3) " +
                "RETURNING id, title, address;")
                .execute(Tuple.of(warehouse.getTitle(), warehouse.getAddress(), company_id),
                        ar -> {
                            if (ar.succeeded()) {
                                future.complete(ar.result().iterator().next().toJson().mapTo(Warehouse.class));
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });
        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Warehouse>> getWarehouses(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<Warehouse>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("SELECT id, title, address " +
                "FROM db_warehouse " +
                "WHERE company_id = $1").execute(
                Tuple.of(company_id), ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<Warehouse> warehouses = new CopyOnWriteArrayList<>();

                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> warehouses.add(row.toJson().mapTo(Warehouse.class)));
                        }

                        future.complete(warehouses);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<Warehouse> updateWarehouseInfo(Warehouse warehouse) {
        CompletableFuture<Warehouse> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("UPDATE db_warehouse " +
                "SET title = $1, address= $2 " +
                "WHERE id = $3 " +
                "RETURNING id, title, address").execute(
                Tuple.of(warehouse.getTitle(), warehouse.getAddress(), warehouse.getId()),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Warehouse.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                }
        );
        return future;
    }
}
