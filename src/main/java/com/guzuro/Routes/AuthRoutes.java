package com.guzuro.Routes;

import com.guzuro.handlers.AuthHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class AuthRoutes {
    public static Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post("register").handler(AuthHandler::register);
        router.post("login").handler(AuthHandler::login);
        router.get("isAuth").handler(AuthHandler::isAuth);

        return router;
    }
}
