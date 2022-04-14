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
        router.post("/getAll").handler(rc -> this.incomeDocHandler.getAll(rc));
        router.post("/get").handler(rc -> this.incomeDocHandler.get(rc));
        router.post("/pay").handler(rc -> this.incomeDocHandler.pay(rc));
//        router.post("/update").handler(rc -> this.incomeDocHandler.update(rc));
//        router.post("/delete").handler(rc -> this.categoryHandler.delete(rc));

        return router;
    }
}
