package com.guzuro.Daos.IncomeDocDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.OrderDao.OrderDao;
import com.guzuro.Daos.OrderDao.PostgresOrderDaoImpl;
import com.guzuro.Dto.IncomeDocumentDto;
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
    public CompletableFuture<CopyOnWriteArrayList<IncomeDocumentDto>> getIncomeDocs(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<IncomeDocumentDto>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "SELECT db_income_document.id, db_income_document.created_at, " +
                "db_income_document.company_id, db_income_document.is_payed, db_order.total, " +
                "db_supplier.name " +
                "FROM db_income_document " +
                "inner JOIN db_order " +
                "ON db_order.order_id = db_income_document.order_id " +
                "inner JOIN db_supplier " +
                "ON db_supplier.id = db_income_document.supplier_id " +
                "WHERE db_income_document.company_id = $1")
                .execute(Tuple.of(company_id),
                        ar -> {
                            if (ar.succeeded()) {
                                CopyOnWriteArrayList<IncomeDocumentDto> incomeDocumentDtos = new CopyOnWriteArrayList<>();

                                if (ar.result().rowCount() > 0) {
                                    ar.result().forEach(row -> incomeDocumentDtos.add(row.toJson().mapTo(IncomeDocumentDto.class)));
                                }

                                future.complete(incomeDocumentDtos);
                            } else {
                               future.completeExceptionally(ar.cause());
                            }
                        });

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



