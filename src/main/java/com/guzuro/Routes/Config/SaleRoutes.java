package com.guzuro.Routes.Config;

import com.guzuro.handlers.AuthHandler;
import com.guzuro.handlers.Config.SaleHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SaleRoutes {
    SaleHandler saleHandler;
    Vertx vertx;

    public SaleRoutes(Vertx vertx) {
        this.saleHandler = new SaleHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/get").handler(rc -> this.saleHandler.get(rc));
        router.post("/add").handler(rc -> this.saleHandler.add(rc));
        router.post("/update").handler(rc -> this.saleHandler.update(rc));
        router.post("/delete").handler(rc -> this.saleHandler.delete(rc));

        return router;
    }
}
