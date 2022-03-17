package com.guzuro.handlers.Config;

import com.guzuro.Daos.Config.WarehouseDao.PostgresWarehouseDaoImpl;
import com.guzuro.Daos.Config.WarehouseDao.Warehouse;
import com.guzuro.Daos.Config.WarehouseDao.WarehouseDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class WarehouseHandler {
    WarehouseDao dao;

    public WarehouseHandler(Vertx vertx) {
        this.dao = new PostgresWarehouseDaoImpl(vertx);
    }

    public void addWarehouse(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        JsonObject reqBody = routingContext.getBodyAsJson();

        Warehouse warehouse = new Warehouse();
        warehouse.setTitle(reqBody.getJsonObject("warehouse").getString("title"));
        warehouse.setAddress(reqBody.getJsonObject("warehouse").getString("address"));

        int company_id = reqBody.getInteger("company_id");

        this.dao.addWarehouse(warehouse, company_id)
                .thenAccept(resWarehouse -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(resWarehouse).encodePrettily());
                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void getWarehouses(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.dao.getWarehouses(company_id)
                .thenAccept(warehouses -> {
                    JsonArray warehousesJson = new JsonArray();
                    if (warehouses.size() > 0)
                    warehouses.forEach(warehouse -> warehousesJson.add(JsonObject.mapFrom(warehouse)));

                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(warehousesJson.encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void updateWarehouseInfo(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        Warehouse warehouse = routingContext.getBodyAsJson().getJsonObject("warehouse").mapTo(Warehouse.class);

        this.dao.updateWarehouseInfo(warehouse)
                .thenAccept(resWarehouse -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(resWarehouse).encodePrettily());
                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

}
