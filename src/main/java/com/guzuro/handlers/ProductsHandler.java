package com.guzuro.handlers;

import com.guzuro.Daos.ProductDao.PostgresProductDaoImpl;
import com.guzuro.Daos.ProductDao.Product;
import com.guzuro.Daos.ProductDao.ProductDao;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        System.out.println(context);
    }

    public void getProductById(RoutingContext context) {
        System.out.println(context);
    }

    public void removeProduct(RoutingContext context) {
        System.out.println(context);
    }

    public void updateProduct(RoutingContext context) {
        System.out.println(context);
    }
}
