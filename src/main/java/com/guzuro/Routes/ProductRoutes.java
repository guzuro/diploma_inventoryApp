package com.guzuro.Routes;

import com.guzuro.handlers.ProductsHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class ProductRoutes {

    public static Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);

        /**
         * get all products
         */
        router.get("/").handler(ProductsHandler::getProducts);
        /**
         * add new product
         */
        router.post("/").handler(ProductsHandler::addProduct);
        /**
         * get product by id
         */
        router.get("/product/:id").handler(ProductsHandler::getProductById);
        /**
         * update product
         */
        router.put("/product/:id").handler(ProductsHandler::updateProduct);
        /**
         * remove product
         */
        router.delete("/product/:id").handler(ProductsHandler::removeProduct);

        return router;
    }
}
