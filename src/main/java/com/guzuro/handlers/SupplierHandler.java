package com.guzuro.handlers;

import com.guzuro.Daos.SupplierDao.PostgresSupplierDaoImpl;
import com.guzuro.Daos.SupplierDao.Supplier;
import com.guzuro.Daos.SupplierDao.SupplierDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class SupplierHandler {
    SupplierDao dao;

    public SupplierHandler(Vertx vertx) {
        this.dao = new PostgresSupplierDaoImpl(vertx);
    }

    public void addSupplier(RoutingContext context) {
        HttpServerResponse response = context.response();

        Supplier supplier = context.getBodyAsJson().getJsonObject("supplier").mapTo(Supplier.class);
        int company_id = context.getBodyAsJson().getInteger("company_id");

        this.dao.createSupplier(supplier, company_id)
                .thenAccept(resSupplier -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(resSupplier).encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;

        });

    }

    public void getSupplier(RoutingContext context) {
        HttpServerResponse response = context.response();

        int supplier_id = context.getBodyAsJson().getInteger("supplier_id");

        this.dao.getSupplier(supplier_id).thenAccept(supplier -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(supplier).encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });

    }

    public void getSuppliers(RoutingContext context) {
        HttpServerResponse response = context.response();

        int company_id = context.getBodyAsJson().getInteger("company_id");

        this.dao.getSuppliers(company_id).thenAccept(suppliers -> {
            JsonArray suppliersJson = new JsonArray();
            if (suppliers.size() > 0) {
                suppliers.forEach(warehouse -> suppliersJson.add(JsonObject.mapFrom(warehouse)));
            }

            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(suppliersJson.encodePrettily());
        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });

    }

    public void deleteSupplier(RoutingContext context) {
        HttpServerResponse response = context.response();

        int supplier_id = context.getBodyAsJson().getInteger("supplier_id");

        this.dao.deleteSupplier(supplier_id)
                .thenAccept(aBoolean -> {
                    if (aBoolean) {
                        response.putHeader("content-type", "application/json; charset=UTF-8")
                                .setStatusCode(200)
                                .end();
                    } else {
                        response.putHeader("content-type", "application/json; charset=UTF-8")
                                .setStatusCode(404)
                                .end();
                    }
                })
                .exceptionally(throwable -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(500)
                            .end(throwable.getMessage());
                    return null;
                });
    }

    public void updateSupplier(RoutingContext context) {
        HttpServerResponse response = context.response();

        Supplier supplier = context.getBodyAsJson().getJsonObject("supplier").mapTo(Supplier.class);

        this.dao.updateSupplier(supplier)
                .thenAccept(resSupplier -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(resSupplier).encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;

        });

    }
}
