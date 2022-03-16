package com.guzuro.handlers.Config;

import com.guzuro.Daos.Config.Sales.PostgresSaleDaoImpl;
import com.guzuro.Daos.Config.Sales.Sale;
import com.guzuro.Daos.Config.Sales.SaleDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class SaleHandler {
    SaleDao saleDao;

    public SaleHandler(Vertx vertx) {
        this.saleDao = new PostgresSaleDaoImpl(vertx);
    }

    public void add(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        Sale sale = routingContext.getBodyAsJson().getJsonObject("sale").mapTo(Sale.class);
        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.saleDao.addSale(sale, company_id).thenAccept(resSale -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resSale).encodePrettily());
        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void get(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.saleDao.getSales(company_id).thenAccept(sales -> {
            JsonArray salesJson = new JsonArray();
            if (sales.size() > 0)
                sales.forEach(warehouse -> salesJson.add(JsonObject.mapFrom(warehouse)));

            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(salesJson.encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void update(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        Sale sale = routingContext.getBodyAsJson().mapTo(Sale.class);

        this.saleDao.updateSale(sale).thenAccept(resSale -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resSale).encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void delete(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int sale_id = routingContext.getBodyAsJson().getInteger("sale_id");

        this.saleDao.deleteSale(sale_id).thenAccept(aBoolean -> {
            if (aBoolean) {
                response.putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusCode(200)
                        .end();
            } else {
                response.putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusCode(404)
                        .end();
            }
        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }
}
