package com.guzuro.handlers;

import com.guzuro.Daos.ProductDao.PostgresProductDaoImpl;
import com.guzuro.Daos.ProductDao.Product;
import com.guzuro.Daos.ProductDao.ProductDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductsHandler {
    ProductDao dao;

    public ProductsHandler(Vertx vertx) {
        this.dao = new PostgresProductDaoImpl(vertx);
    }

    public void getProducts(RoutingContext context) {
        HttpServerResponse response = context.response();

        this.dao.getAllProducts(context.getBodyAsJson().getInteger("company_id"))
                .thenAccept(products -> {
                    JsonArray productJson = new JsonArray();
                    products.forEach(employee -> productJson.add(JsonObject.mapFrom(employee)));
                    context.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=UTF-8")
                            .end(productJson.encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void addProduct(RoutingContext context) {
        HttpServerResponse response = context.response();

        Product product = context.getBodyAsJson().mapTo(Product.class);

        this.dao.addProduct(product).thenAccept(resProduct -> {
            context.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=UTF-8")
                    .end(JsonObject.mapFrom(resProduct).encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void getProductBySku(RoutingContext context) {
        HttpServerResponse response = context.response();

        long sku = context.getBodyAsJson().getLong("sku");

        this.dao.getProductBySku(sku).thenAccept(product -> {
            context.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=UTF-8")
                    .end(JsonObject.mapFrom(product).encodePrettily());
        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void removeProduct(RoutingContext context) {
        HttpServerResponse response = context.response();

        long sku = context.getBodyAsJson().getLong("sku");

        this.dao.removeProduct(sku).thenAccept(product -> {
            context.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=UTF-8")
                    .end();
        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void updateProduct(RoutingContext context) {
        HttpServerResponse response = context.response();

        Product product = context.getBodyAsJson().mapTo(Product.class);

        this.dao.updateProduct(product).thenAccept(resProduct -> {
            context.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=UTF-8")
                    .end(JsonObject.mapFrom(resProduct).encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500).end(throwable.getMessage());
            return null;
        });

    }
}
