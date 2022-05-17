package com.guzuro.Daos.StatisticsDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class PostgresStatisticsDao implements StatisticsDao {
    SqlClient pgClient;

    public PostgresStatisticsDao(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    public CompletableFuture<Boolean> addIncomeRecord(Statistics record, int company_id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "INSERT INTO db_stat (date, income_sum, company_id)" +
                "VALUES ($1, $2, $3);")
                .execute(Tuple.of(record.getDate(), record.getIncome_sum(), company_id),
                        ar -> {
                            if (ar.succeeded()) {
                                future.complete(true);
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });
        return future;
    }

    public CompletableFuture<Boolean> addSpendRecord(Statistics record, int company_id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "INSERT INTO db_stat (date, spend_sum, company_id)" +
                "VALUES ($1, $2, $3);")
                .execute(Tuple.of(record.getDate(), record.getSpend_sum(), company_id),
                        ar -> {
                            if (ar.succeeded()) {
                                future.complete(true);
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });
        return future;
    }

    @Override
    public CompletableFuture<ArrayList<Statistics>> getStat(int company_id) {
        CompletableFuture<ArrayList<Statistics>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "SELECT date, " +
                "SUM(db_stat.income_sum) as income_sum, " +
                "SUM(db_stat.spend_sum) as spend_sum " +
                "FROM public.db_stat " +
                "WHERE company_id = $1 " +
                "AND " +
                "date <= CURRENT_DATE " +
                "AND " +
                "date >= (CURRENT_DATE - interval '1 day' * 7) " +
                "GROUP BY date;")
                .execute(Tuple.of(company_id), ar -> {
                    if (ar.succeeded()) {
                        ArrayList<Statistics> statisticsList = new ArrayList<>();

                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> statisticsList.add(row.toJson().mapTo(Statistics.class)));
                        }

                        future.complete(statisticsList);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }
}



