package com.guzuro.Routes;

import com.guzuro.handlers.ProductsHandler;
import com.guzuro.handlers.UserHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class UserRoutes {
    public static Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);

        router.get("/:id").handler(UserHandler::getUserInfo);
        router.post("/add").handler(UserHandler::addUser);
        router.post("/changeRole").handler(UserHandler::changeUserRole);
        router.delete("/user/:id").handler(UserHandler::deleteUser);
        router.put("/user/:id").handler(UserHandler::updateUser);

        return router;
    }

}
