package com.guzuro.Daos.CompanyDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;

import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;

public class PostgresCompanyDaoImpl implements CompanyDao {
    SqlClient pgClient;

    public PostgresCompanyDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<Company> createCompany(Company company) {
        return null;
    }

    @Override
    public CompletableFuture<Company> getCompany(Number owner) {
        CompletableFuture<Company> fut = new CompletableFuture<>();

        this.pgClient.preparedQuery("SELECT id, name, inn, phone, email, country, currency " +
                "FROM db_company WHERE owner = $1").execute(Tuple.of(owner), ar -> {
            if (ar.succeeded()) {
                if (ar.result().rowCount() > 0) {
                    fut.complete(ar.result().iterator().next().toJson().mapTo(Company.class));
                }
            } else {
                fut.completeExceptionally(ar.cause());
            }
        });
        return fut;
    }

    @Override
    public CompletableFuture<Company> updateCompanyInfo(Company company) {
        return null;
    }
}
