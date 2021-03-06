package com.guzuro.Routes;

import com.guzuro.handlers.AuthHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class AuthRoutes {

    AuthHandler authHandler;
    Vertx vertx;

    public AuthRoutes(Vertx vertx) {
        this.authHandler = new AuthHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/register").handler(rc -> this.authHandler.register(rc));
        router.post("/login").handler(rc -> this.authHandler.login(rc));
        router.post("/isauth").handler(rc -> this.authHandler.isAuth(rc));
        router.post("/logout").handler(rc -> this.authHandler.logout(rc));

        return router;
    }
}
