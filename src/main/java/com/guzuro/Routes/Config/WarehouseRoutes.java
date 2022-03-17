package com.guzuro.Routes.Config;

import com.guzuro.handlers.Config.WarehouseHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class WarehouseRoutes {

    WarehouseHandler warehouseHandler;

    Vertx vertx;

    public WarehouseRoutes(Vertx vertx) {
        this.warehouseHandler = new WarehouseHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes() {
        Router router = Router.router(this.vertx);

        router.post("/add").handler(rc -> this.warehouseHandler.addWarehouse(rc));
        router.post("/get").handler(rc -> this.warehouseHandler.getWarehouses(rc));
        router.post("/update").handler(rc -> this.warehouseHandler.updateWarehouseInfo(rc));
//        router.post("/delete").handler(rc -> this.warehouseHandler.deleteWarehouse(rc));

        return router;
    }
}
