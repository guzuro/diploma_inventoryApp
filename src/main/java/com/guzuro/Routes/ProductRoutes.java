package com.guzuro.Routes;

import com.guzuro.handlers.CompanyHandler;
import com.guzuro.handlers.ProductsHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class ProductRoutes {
    ProductsHandler productsHandler;

    Vertx vertx;

    public ProductRoutes(Vertx vertx) {
        this.productsHandler = new ProductsHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes(Vertx vertx) {
        Router router = Router.router(vertx);

        router.post("/add").handler(rc -> this.productsHandler.addProduct(rc));
        router.post("/getproducts").handler(rc -> this.productsHandler.getProducts(rc));
        router.post("/getproduct").handler(rc -> this.productsHandler.getProductById(rc));
        router.post("/updateproduct").handler(rc -> this.productsHandler.updateProduct(rc));
        router.post("/removeproduct").handler(rc -> this.productsHandler.removeProduct(rc));

        return router;
    }
}
