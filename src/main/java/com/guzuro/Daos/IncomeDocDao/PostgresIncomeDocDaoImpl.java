package com.guzuro.Daos.IncomeDocDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresIncomeDocDaoImpl implements IncomeDocDao {
    SqlClient pgClient;

    public PostgresIncomeDocDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<IncomeDoc> addIncomeDoc(IncomeDoc incomeDoc, int company_id) {
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

    @Override
    public CompletableFuture<CopyOnWriteArrayList<IncomeDoc>> getIncomeDocs(int company_id) {
        return null;
    }
}



