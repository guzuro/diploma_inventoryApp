package com.guzuro.handlers;

import io.vertx.ext.web.RoutingContext;

public class ProductsHandler {

    public static void getProducts(RoutingContext context) {
        System.out.println(context);
    }

    public static void addProduct(RoutingContext context) {
        System.out.println(context);
    }

    public static void getProductById(RoutingContext context) {
        System.out.println(context);
    }

    public static void removeProduct(RoutingContext context) {
        System.out.println(context);
    }

    public static void updateProduct(RoutingContext context) {
        System.out.println(context);
    }
}
