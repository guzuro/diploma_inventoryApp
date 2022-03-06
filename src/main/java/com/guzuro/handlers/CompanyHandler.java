package com.guzuro.handlers;

import com.guzuro.Daos.CompanyDao.CompanyDao;
import com.guzuro.Daos.CompanyDao.PostgresCompanyDaoImpl;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class CompanyHandler {

    CompanyDao dao;

    public CompanyHandler(Vertx vertx) {
        this.dao = new PostgresCompanyDaoImpl(vertx);
    }

    public void getCompany(RoutingContext context) {
        Number owner = context.getBodyAsJson().getNumber("owner");
        HttpServerResponse response = context.response();

        this.dao.getCompany(owner)
                .thenAccept(company -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(201)
                            .end(JsonObject.mapFrom(company).encodePrettily());
                }).exceptionally(throwable -> {
            response.setStatusCode(500)
                    .putHeader("content-type", "application/json; charset=UTF-8")
                    .end(throwable.getCause().getMessage());
            return null;
        });
    }

    public void updateCompany(RoutingContext context) {

    }

}
