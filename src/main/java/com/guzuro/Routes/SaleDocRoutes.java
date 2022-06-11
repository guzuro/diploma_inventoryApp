package com.guzuro.Routes;

import com.guzuro.handlers.IncomeDocHandler;
import com.guzuro.handlers.SaleDocHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SaleDocRoutes {
    SaleDocHandler saleDocHandler;
    Vertx vertx;

    public SaleDocRoutes(Vertx vertx) {
        this.saleDocHandler = new SaleDocHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/add").handler(rc -> this.saleDocHandler.addSaleDocument(rc));
        router.post("/getAll").handler(rc -> this.saleDocHandler.getAll(rc));
        router.post("/get").handler(rc -> this.saleDocHandler.get(rc));
        router.post("/pay").handler(rc -> this.saleDocHandler.pay(rc));
//        router.post("/update").handler(rc -> this.incomeDocHandler.update(rc));
//        router.post("/delete").handler(rc -> this.categoryHandler.delete(rc));

        return router;
    }
}
