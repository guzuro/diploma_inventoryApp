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
        CompletableFuture<Company> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("" +
                "INSERT INTO db_company (name, inn, phone, email, country, currency) " +
                "VALUES ($1, $2, $3, $4, $5, $6) " +
                "RETURNING id, name, inn, phone, email, country, currency;")
                .execute(Tuple.of(company.getName(), company.getInn(), company.getPhone(),
                        company.getEmail(), company.getCountry(), company.getCurrency()), ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(Company.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<Company> getCompany(Number company_id) {
        CompletableFuture<Company> fut = new CompletableFuture<>();
        this.pgClient.preparedQuery("SELECT id, name, inn, phone, email, country, currency " + "FROM db_company WHERE id = $1").execute(Tuple.of(company_id), ar -> {
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
        CompletableFuture<Company> fut = new CompletableFuture<>();

        this.pgClient.preparedQuery("UPDATE db_company " +
                "SET name=$1, inn=$2, phone=$3, email=$4, country=$5, currency=$6 " +
                "WHERE id=$7" +
                "RETURNING id, name, inn, phone, email, country, currency")
                .execute(Tuple.of(
                        company.getName(),
                        company.getInn(),
                        company.getPhone(),
                        company.getEmail(),
                        company.getCountry(),
                        company.getCurrency(),
                        company.getId()), ar -> {
                    if (ar.succeeded()) {
                        Company resCompany = ar.result().iterator().next().toJson().mapTo(Company.class);
                        fut.complete(resCompany);
                    } else {
                        fut.completeExceptionally(ar.cause());
                    }
                });
        return fut;
    }
}



