package com.guzuro.Daos.IncomeDocDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.OrderDao.Order;
import com.guzuro.Daos.OrderDao.OrderDao;
import com.guzuro.Daos.OrderDao.PostgresOrderDaoImpl;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresIncomeDocDaoImpl implements IncomeDocDao {
    SqlClient pgClient;
    OrderDao orderDao;

    public PostgresIncomeDocDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
        this.orderDao = new PostgresOrderDaoImpl(vertx);
    }

    @Override
    public CompletableFuture<IncomeDoc> addIncomeDoc(IncomeDoc incomeDoc, int companyId) {
        CompletableFuture<IncomeDoc> future = new CompletableFuture<>();
        this.orderDao.addOrder(incomeDoc.getOrder())
                .thenAccept(order -> {
                    this.pgClient.preparedQuery("" +
                            "INSERT INTO db_income_document(order_id, company_id, supplier_id, created_at) " +
                            "VALUES ($1, $2, $3, $4) RETURNING created_at, id;")
                            .execute(Tuple.of(order.getOrder_id(), companyId,
                                    incomeDoc.getSupplier().getId(), incomeDoc.getCreated_at()),
                                    ar -> {
                                        if (ar.succeeded()) {
                                            IncomeDoc id = ar.result().iterator().next().toJson().mapTo(IncomeDoc.class);
                                            id.setSupplier(incomeDoc.getSupplier());
                                            id.setOrder(order);
                                            future.complete(id);
                                        } else {
                                            future.completeExceptionally(ar.cause());
                                        }
                                    });
                })
                .exceptionally(a -> {
                    future.completeExceptionally(a);
                    return null;
                });
        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<IncomeDoc>> getIncomeDocs(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<IncomeDoc>> future = new CompletableFuture<>();



        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<IncomeDoc>> getIncomeDocBySupplier(int supplier_id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteIncomeDoc(int incomeDoc_id) {
        return null;
    }

    @Override
    public CompletableFuture<IncomeDoc> updateIncomeDoc(IncomeDoc incomeDoc) {
        return null;
    }
}



