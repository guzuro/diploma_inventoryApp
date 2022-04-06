package com.guzuro.Daos.SupplierDao;

import com.guzuro.Daos.Config.Category.Category;
import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresSupplierDaoImpl implements SupplierDao {
    SqlClient pgClient;

    public PostgresSupplierDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<Supplier> createSupplier(Supplier supplier, int company_id) {
        CompletableFuture<Supplier> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "INSERT INTO db_supplier (name, phone, address, inn, company_id) " +
                "VALUES ($1, $2, $3, $4, $5)" +
                "RETURNING id, name, phone, address, inn;")
                .execute(Tuple.of(supplier.getName(), supplier.getPhone(), supplier.getAddress(),
                        supplier.getInn(), company_id),
                        ar -> {
                            if (ar.succeeded()) {
                                future.complete(ar.result().iterator().next().toJson().mapTo(Supplier.class));
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });

        return future;
    }

    @Override
    public CompletableFuture<Supplier> getSupplier(int supplier_id) {
        CompletableFuture<Supplier> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "SELECT id, name, phone, address, inn " +
                "FROM db_supplier " +
                "WHERE id = $1")
                .execute(Tuple.of(supplier_id),
                        ar -> {
                            if (ar.succeeded()) {
                                if (ar.result().rowCount() == 1) {
                                    future.complete(ar.result().iterator().next().toJson().mapTo(Supplier.class));
                                } else {
                                    future.completeExceptionally(new Throwable("NOT FOUND"));
                                }
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });

        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Supplier>> getSuppliers(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<Supplier>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "SELECT id, name, phone, address, inn " +
                "FROM db_supplier " +
                "WHERE company_id = $1"
        ).execute(Tuple.of(company_id),
                ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<Supplier> suppliersList = new CopyOnWriteArrayList<>();

                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> suppliersList.add(row.toJson().mapTo(Supplier.class)));
                        }

                        future.complete(suppliersList);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });

        return future;
    }

    @Override
    public CompletableFuture<Boolean> deleteSupplier(int supplier_id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        this.pgClient.preparedQuery("DELETE FROM db_supplier WHERE id = $1;")
                .execute(Tuple.of(supplier_id), ar -> {
                    if (ar.succeeded()) {
                        future.complete(true);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<Supplier> updateSupplier(Supplier supplier) {
        CompletableFuture<Supplier> future = new CompletableFuture<>();
        this.pgClient.preparedQuery("UPDATE db_supplier " +
                "SET name=$1, phone=$2, address=$3, inn=$4 " +
                "WHERE id = $5 " +
                "RETURNING id, name, phone, address, inn;")
                .execute(
                        Tuple.of(supplier.getName(), supplier.getPhone(), supplier.getAddress(),
                                supplier.getInn()),
                        ar -> {
                            if (ar.succeeded()) {
                                future.complete(ar.result().iterator().next().toJson().mapTo(Supplier.class));
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        }
                );
        return future;
    }
}



