package com.guzuro;

import com.guzuro.handlers.ProductsHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {


    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.get("/api/products").handler(ProductsHandler::getProducts);
        router.post("/api/products/").handler(ProductsHandler::addProduct);
        router.get("/api/products/:id").handler(ProductsHandler::getProductById);
        router.put("/api/products/:id").handler(ProductsHandler::updateProduct);

        router.get("/").handler(routingContext -> {
            routingContext.response()
                    .putHeader("content-type", "text/html")
                    .setStatusCode(200)
                    .end("Main page");
        });


        server.requestHandler(router).listen(8888, httpServerAsyncResult -> {
            if (httpServerAsyncResult.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8888");
            } else {
                startPromise.fail(httpServerAsyncResult.cause());
            }
        });
    }
}
