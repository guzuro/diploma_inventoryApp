package com.guzuro.handlers;

import com.guzuro.Daos.StatisticsDao.PostgresStatisticsDao;
import com.guzuro.Daos.StatisticsDao.StatisticsDao;
import com.guzuro.Daos.SupplierDao.PostgresSupplierDaoImpl;
import com.guzuro.Daos.SupplierDao.Supplier;
import com.guzuro.Daos.SupplierDao.SupplierDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class StatisticsHandler {
    StatisticsDao dao;

    public StatisticsHandler(Vertx vertx) {
        this.dao = new PostgresStatisticsDao(vertx);
    }

    public void getStatistics(RoutingContext context) {
        HttpServerResponse response = context.response();

        int company_id = context.getBodyAsJson().getInteger("company_id");

        this.dao.getStat(company_id)
                .thenAccept(statistics -> {

                    JsonArray statisticsJson = new JsonArray();
                    if (statistics.size() > 0)
                        statistics.forEach(warehouse -> statisticsJson.add(JsonObject.mapFrom(warehouse)));

                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(statisticsJson.encodePrettily());


                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(statistics).encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;

        });

    }
}
