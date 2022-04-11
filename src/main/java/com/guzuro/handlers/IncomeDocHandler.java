package com.guzuro.handlers;

import com.guzuro.Daos.IncomeDocDao.IncomeDoc;
import com.guzuro.Daos.IncomeDocDao.IncomeDocDao;
import com.guzuro.Daos.IncomeDocDao.PostgresIncomeDocDaoImpl;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class IncomeDocHandler {
    IncomeDocDao incomeDocDao;

    public IncomeDocHandler(Vertx vertx) {
        this.incomeDocDao = new PostgresIncomeDocDaoImpl(vertx);
    }

    public void addIncomeDocument(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        try {
            IncomeDoc incomeDoc = routingContext.getBodyAsJson().getJsonObject("doc").mapTo(IncomeDoc.class);
            int companyId = routingContext.getBodyAsJson().getInteger("company_id");

            this.incomeDocDao.addIncomeDoc(incomeDoc, companyId)
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


        } catch (Exception e) {
            System.out.println(e);
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(e.toString());
        }
//            response.putHeader("content-type", "application/json; charset=UTF-8")
//                    .setStatusCode(200)
//                    .end(JsonObject.mapFrom(resCategory).encodePrettily());
//        }).exceptionally(throwable -> {
//            response.putHeader("content-type", "application/json; charset=UTF-8")
//                    .setStatusCode(500)
//                    .end(throwable.getMessage());
//            return null;
//        });
    }

    public void get(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.incomeDocDao.getIncomeDocs(company_id).thenAccept(categories -> {
            JsonArray categoriesJson = new JsonArray();
            if (categories.size() > 0)
                categories.forEach(warehouse -> categoriesJson.add(JsonObject.mapFrom(warehouse)));

            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(categoriesJson.encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }
//
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
