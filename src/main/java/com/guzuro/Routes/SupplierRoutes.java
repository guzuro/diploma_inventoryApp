package com.guzuro.Routes;

import com.guzuro.handlers.Config.CategoryHandler;
import com.guzuro.handlers.SupplierHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SupplierRoutes {
    SupplierHandler supplierHandler;
    Vertx vertx;

    public SupplierRoutes(Vertx vertx) {
        this.supplierHandler = new SupplierHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/getSuppliers").handler(rc -> this.supplierHandler.getSuppliers(rc));
        router.post("/getSupplier").handler(rc -> this.supplierHandler.getSupplier(rc));
        router.post("/add").handler(rc -> this.supplierHandler.addSupplier(rc));
        router.post("/delete").handler(rc -> this.supplierHandler.deleteSupplier(rc));
        router.post("/update").handler(rc -> this.supplierHandler.updateSupplier(rc));

        return router;
    }
}
