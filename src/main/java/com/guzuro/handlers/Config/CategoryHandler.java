package com.guzuro.handlers.Config;

import com.guzuro.Daos.Config.Category.Category;
import com.guzuro.Daos.Config.Category.CategoryDao;
import com.guzuro.Daos.Config.Category.PostgresCategoryDaoImpl;
import com.guzuro.Daos.Config.Sales.Sale;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class CategoryHandler {
    CategoryDao categoryDao;

    public CategoryHandler(Vertx vertx) {
        this.categoryDao = new PostgresCategoryDaoImpl(vertx);
    }

    public void add(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        Category category = routingContext.getBodyAsJson().getJsonObject("category").mapTo(Category.class);
        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.categoryDao.addCategory(category, company_id).thenAccept(resCategory -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resCategory).encodePrettily());
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

        this.categoryDao.getCategories(company_id).thenAccept(categories -> {
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

    public void update(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        Category category = routingContext.getBodyAsJson().getJsonObject("category").mapTo(Category.class);

        this.categoryDao.updateCategory(category).thenAccept(resCategory -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resCategory).encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void delete(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int category_id = routingContext.getBodyAsJson().getInteger("category_id");

        this.categoryDao.deleteCategory(category_id).thenAccept(aBoolean -> {
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
