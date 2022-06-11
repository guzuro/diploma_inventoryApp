package com.guzuro.Daos.SaleDocDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.OrderDao.OrderDao;
import com.guzuro.Daos.OrderDao.OrderLine.OrderLine;
import com.guzuro.Daos.OrderDao.PostgresOrderDaoImpl;
import com.guzuro.Daos.ProductDao.PostgresProductDaoImpl;
import com.guzuro.Daos.ProductDao.ProductDao;
import com.guzuro.Daos.StatisticsDao.PostgresStatisticsDao;
import com.guzuro.Daos.StatisticsDao.Statistics;
import com.guzuro.Daos.StatisticsDao.StatisticsDao;
import com.guzuro.Daos.SupplierDao.PostgresSupplierDaoImpl;
import com.guzuro.Daos.SupplierDao.SupplierDao;
import com.guzuro.Dto.IncomeDocumentDto;
import com.guzuro.Dto.SaleDocumentDto;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresSaleDocDaoImpl implements SaleDocDao {
    SqlClient pgClient;
    OrderDao orderDao;
    ProductDao productDao;
    StatisticsDao statisticsDao;

    public PostgresSaleDocDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
        this.orderDao = new PostgresOrderDaoImpl(vertx);
        this.productDao = new PostgresProductDaoImpl(vertx);
        this.statisticsDao = new PostgresStatisticsDao(vertx);
    }

    @Override
    public CompletableFuture<SaleDoc> addSaleDoc(SaleDoc saleDoc, int companyId) {
        CompletableFuture<SaleDoc> future = new CompletableFuture<>();
        this.orderDao.addOrder(saleDoc.getOrder())
                .thenAccept(order -> {
                    this.pgClient.preparedQuery("" +
                            "INSERT INTO db_sale_document(order_id, company_id, client_name, created_at) " +
                            "VALUES ($1, $2, $3, $4) " +
                            "RETURNING created_at, id;")
                            .execute(Tuple.of(order.getOrder_id(), companyId,
                                    saleDoc.getClient_name(), saleDoc.getCreated_at()),
                                    ar -> {
                                        if (ar.succeeded()) {
                                            SaleDoc id = ar.result().iterator().next().toJson().mapTo(SaleDoc.class);
                                            id.setClient_name(saleDoc.getClient_name());
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
    public CompletableFuture<CopyOnWriteArrayList<SaleDocumentDto>> getSaleDocs(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<SaleDocumentDto>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "SELECT db_sale_document.id, db_sale_document.created_at, " +
                "db_sale_document.is_payed, db_order.total, " +
                "db_sale_document.client_name " +
                "FROM db_sale_document " +
                "LEFT JOIN db_order " +
                "ON db_order.order_id = db_sale_document.order_id " +
                "WHERE db_sale_document.company_id = $1" +
                "ORDER BY db_sale_document.id DESC;")
                .execute(Tuple.of(company_id),
                        ar -> {
                            if (ar.succeeded()) {
                                CopyOnWriteArrayList<SaleDocumentDto> saleDocumentDtos = new CopyOnWriteArrayList<>();

                                if (ar.result().rowCount() > 0) {
                                    ar.result().forEach(row -> saleDocumentDtos.add(row.toJson().mapTo(SaleDocumentDto.class)));
                                }

                                future.complete(saleDocumentDtos);
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });

        return future;
    }

    @Override
    public CompletableFuture<SaleDoc> getSaleDoc(int saleDocId) {
        CompletableFuture<SaleDoc> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "SELECT id, created_at, company_id, is_payed, client_name, order_id " +
                "FROM db_sale_document " +
                "WHERE id = $1;")
                .execute(Tuple.of(saleDocId),
                        ar -> {
                            if (ar.succeeded()) {
                                JsonObject jsonObject = ar.result().iterator().next().toJson();
                                SaleDoc incomeDoc = new SaleDoc();

                                incomeDoc.setId(jsonObject.getInteger("id"));
                                incomeDoc.setCreated_at(jsonObject.getString("created_at"));
                                incomeDoc.setIs_payed(jsonObject.getBoolean("is_payed"));
                                incomeDoc.setClient_name(jsonObject.getString("client_name"));

                                this.orderDao.getOrder(jsonObject.getInteger("order_id"))
                                        .thenAccept(order -> {
                                            incomeDoc.setOrder(order);
                                            future.complete(incomeDoc);
                                        })
                                        .exceptionally(throwable -> {
                                            future.completeExceptionally(throwable);
                                            return null;
                                        });
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });
        return future;
    }

    @Override
    public CompletableFuture<Boolean> paySaleDoc(int saleDocId, int company_id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Boolean is_payed = true;
        this.pgClient.preparedQuery("" +
                "UPDATE db_sale_document " +
                "SET is_payed = $1 " +
                "WHERE id = $2;")
                .execute(Tuple.of(
                        is_payed, saleDocId
                ), ar -> {
                    if (ar.succeeded()) {
                        this.getSaleDoc(saleDocId)
                                .thenAccept(saleDoc -> {

                                    Statistics statRecord = new Statistics();

                                    statRecord.setIncome_sum(saleDoc.getOrder().getTotal());
                                    statRecord.setDate(LocalDate.now());

                                    CopyOnWriteArrayList<OrderLine> lines = saleDoc.getOrder().getOrderLines();
                                    CopyOnWriteArrayList<CompletableFuture<Boolean>> list = new CopyOnWriteArrayList<>();

                                    lines.forEach(orderLine -> {
                                        list.add(this.productDao.decrementProductQuantity(orderLine.getProduct().getSku(), orderLine.getQuantity()));
                                    });

                                    CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]))
                                            .thenAccept(aVoid -> {
                                                this.statisticsDao.addIncomeRecord(statRecord, company_id)
                                                        .thenAccept(aBoolean -> {
                                                            future.complete(true);
                                                        }).exceptionally(throwable -> {
                                                    future.completeExceptionally(throwable);
                                                    return null;
                                                });
                                            })
                                            .exceptionally(throwable -> {
                                                future.completeExceptionally(throwable);
                                                return null;
                                            });
                                })
                                .exceptionally(throwable -> {
                                    future.completeExceptionally(throwable);
                                    return null;
                                });
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });

        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<SaleDoc>> getIncomeDocBySupplier(int supplier_id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteSaleDoc(int incomeDoc_id) {
        return null;
    }

    @Override
    public CompletableFuture<SaleDoc> updateSaleDoc(SaleDoc incomeDoc) {
        return null;
    }
}



