package com.guzuro.handlers;

import com.guzuro.Daos.IncomeDocDao.IncomeDoc;
import com.guzuro.Daos.IncomeDocDao.IncomeDocDao;
import com.guzuro.Daos.IncomeDocDao.PostgresIncomeDocDaoImpl;
import com.guzuro.Daos.SaleDocDao.PostgresSaleDocDaoImpl;
import com.guzuro.Daos.SaleDocDao.SaleDoc;
import com.guzuro.Daos.SaleDocDao.SaleDocDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class SaleDocHandler {
    SaleDocDao saleDocDao;

    public SaleDocHandler(Vertx vertx) {
        this.saleDocDao = new PostgresSaleDocDaoImpl(vertx);
    }

    public void addSaleDocument(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        SaleDoc saleDoc = routingContext.getBodyAsJson().getJsonObject("doc").mapTo(SaleDoc.class);
        int companyId = routingContext.getBodyAsJson().getInteger("company_id");

        this.saleDocDao.addSaleDoc(saleDoc, companyId)
                .thenAccept(doc -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(doc).encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void getAll(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.saleDocDao.getSaleDocs(company_id).thenAccept(incomeDocuments -> {
            JsonArray saleDocumentsJson = new JsonArray();
            if (incomeDocuments.size() > 0)
                incomeDocuments.forEach(doc -> saleDocumentsJson.add(JsonObject.mapFrom(doc)));

            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(saleDocumentsJson.encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void get(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int saleDocId = routingContext.getBodyAsJson().getInteger("saleDocId");

        this.saleDocDao.getSaleDoc(saleDocId).thenAccept(resDoc -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resDoc).encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void pay(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int saleDocId = routingContext.getBodyAsJson().getInteger("saleDocId");
        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.saleDocDao.paySaleDoc(saleDocId, company_id).thenAccept(aBoolean -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end();

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }


//    public void update(RoutingContext routingContext) {
//        HttpServerResponse response = routingContext.response();
//
//        Category category = routingContext.getBodyAsJson().getJsonObject("category").mapTo(Category.class);
//
//        this.categoryDao.updateCategory(category).thenAccept(resCategory -> {
//            response.putHeader("content-type", "application/json; charset=UTF-8")
//                    .setStatusCode(200)
//                    .end(JsonObject.mapFrom(resCategory).encodePrettily());
//
//        }).exceptionally(throwable -> {
//            response.putHeader("content-type", "application/json; charset=UTF-8")
//                    .setStatusCode(500)
//                    .end(throwable.getMessage());
//            return null;
//        });
//    }
//
//    public void delete(RoutingContext routingContext) {
//        HttpServerResponse response = routingContext.response();
//
//        int category_id = routingContext.getBodyAsJson().getInteger("category_id");
//
//        this.categoryDao.deleteCategory(category_id).thenAccept(aBoolean -> {
//            if (aBoolean) {
//                response.putHeader("content-type", "application/json; charset=UTF-8")
//                        .setStatusCode(200)
//                        .end();
//            } else {
//                response.putHeader("content-type", "application/json; charset=UTF-8")
//                        .setStatusCode(404)
//                        .end();
//            }
//        }).exceptionally(throwable -> {
//            response.putHeader("content-type", "application/json; charset=UTF-8")
//                    .setStatusCode(500)
//                    .end(throwable.getMessage());
//            return null;
//        });
//    }
}
