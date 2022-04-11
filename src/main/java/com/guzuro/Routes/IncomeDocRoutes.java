package com.guzuro.Routes;

import com.guzuro.handlers.Config.CategoryHandler;
import com.guzuro.handlers.IncomeDocHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class IncomeDocRoutes {
    IncomeDocHandler incomeDocHandler;
    Vertx vertx;

    public IncomeDocRoutes(Vertx vertx) {
        this.incomeDocHandler = new IncomeDocHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/add").handler(rc -> this.incomeDocHandler.addIncomeDocument(rc));
//        router.post("/add").handler(rc -> this.categoryHandler.add(rc));
//        router.post("/update").handler(rc -> this.categoryHandler.update(rc));
//        router.post("/delete").handler(rc -> this.categoryHandler.delete(rc));

        return router;
    }
}
