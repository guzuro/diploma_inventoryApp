package com.guzuro.Routes.Config;

import com.guzuro.handlers.Config.CategoryHandler;
import com.guzuro.handlers.Config.SaleHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class CategoryRoutes {
    CategoryHandler categoryHandler;
    Vertx vertx;

    public CategoryRoutes(Vertx vertx) {
        this.categoryHandler = new CategoryHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/get").handler(rc -> this.categoryHandler.get(rc));
        router.post("/add").handler(rc -> this.categoryHandler.add(rc));
        router.post("/update").handler(rc -> this.categoryHandler.update(rc));
        router.post("/delete").handler(rc -> this.categoryHandler.delete(rc));

        return router;
    }
}
