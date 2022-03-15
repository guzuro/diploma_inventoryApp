package com.guzuro.handlers;

import com.guzuro.Daos.CompanyDao.Company;
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

    public void updateCompanyInfo(RoutingContext context) {
        HttpServerResponse response = context.response();
        Company reqCompany = context.getBodyAsJson().mapTo(Company.class);

        this.dao.updateCompanyInfo(reqCompany)
                .thenAccept(company -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(company).encodePrettily());
                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

}
