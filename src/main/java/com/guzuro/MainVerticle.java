package com.guzuro;

import com.guzuro.Routes.AuthRoutes;
import com.guzuro.Routes.ProductRoutes;
import com.guzuro.Routes.UserRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);


        router.route()
                .handler(
                        CorsHandler.create()
                                .allowedMethod(HttpMethod.GET)
                                .allowedMethod(HttpMethod.POST)
                                .allowedMethod(HttpMethod.PUT)
                                .allowedMethod(HttpMethod.OPTIONS)
                                .allowCredentials(true)
                                .allowedHeader("Access-Control-Allow-Headers")
                                .allowedHeader("Access-Control-Allow-Method")
                                .allowedHeader("Access-Control-Allow-Origin")
                                .allowedHeader("Access-Control-Allow-Credentials")
                                .allowedHeader("Content-Type"))
                .handler(
                        BodyHandler
                                .create()
                                .setMergeFormAttributes(true)
                                .setUploadsDirectory("webroot")
                );


        router.route("/api");

        router.mountSubRouter("/products", ProductRoutes.setRoutes(vertx));
        router.mountSubRouter("/user", UserRoutes.setRoutes(vertx));
        router.mountSubRouter("/auth", AuthRoutes.setRoutes(vertx));


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
