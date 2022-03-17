package com.guzuro.Routes.Config;

import com.guzuro.handlers.Config.EmployeeRoleHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class EmployeeRolesRoutes {
    EmployeeRoleHandler employeeRoleHandler;
    Vertx vertx;

    public EmployeeRolesRoutes(Vertx vertx) {
        this.employeeRoleHandler = new EmployeeRoleHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/get").handler(rc -> this.employeeRoleHandler.get(rc));
        router.post("/add").handler(rc -> this.employeeRoleHandler.add(rc));
        router.post("/update").handler(rc -> this.employeeRoleHandler.update(rc));
        router.post("/delete").handler(rc -> this.employeeRoleHandler.delete(rc));

        return router;
    }
}
