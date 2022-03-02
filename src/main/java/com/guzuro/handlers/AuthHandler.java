package com.guzuro.handlers;

import io.vertx.ext.web.RoutingContext;

public class AuthHandler {
    public static void register(RoutingContext context) {
    }

    public static void login(RoutingContext context) {
        System.out.println(context);
    }

    public static void isAuth(RoutingContext context) {
        System.out.println(context);
    }

}
