package com.guzuro.handlers;

import io.vertx.ext.web.RoutingContext;

public class UserHandler {
    public static void getUserInfo(RoutingContext context) {
        System.out.println(context);
    }

    public static void addUser(RoutingContext context) {
        System.out.println(context);
    }

    public static void changeUserRole(RoutingContext context) {
        System.out.println(context);
    }

    public static void updateUser(RoutingContext context) {
        System.out.println(context);
    }

    public static void deleteUser(RoutingContext context) {
        System.out.println(context);
    }
}
