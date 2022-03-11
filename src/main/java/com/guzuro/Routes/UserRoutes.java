package com.guzuro.Routes;

import com.guzuro.handlers.AuthHandler;
import com.guzuro.handlers.ProductsHandler;
import com.guzuro.handlers.UserHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class UserRoutes {
    UserHandler userHandler;
    Vertx vertx;

    public UserRoutes(Vertx vertx) {
        this.vertx = vertx;
        this.userHandler = new UserHandler(vertx);
    }


    public Router setRoutes() {
        Router router = Router.router(this.vertx);

        router.post("/getAll").handler(rc -> this.userHandler.getAllEmployes(rc));
        router.post("/getemployement").handler(rc -> this.userHandler.getEmployement(rc));
        router.post("/add").handler(rc -> this.userHandler.addEmployee(rc));
        router.post("/delete").handler(rc -> this.userHandler.deleteUser(rc));
        router.post("/updateUser").handler(rc -> this.userHandler.updateUser(rc));
        router.post("/updateEmployee").handler(rc -> this.userHandler.updateEmployee(rc));

        return router;
    }

}
