package com.guzuro;

import com.guzuro.Routes.AuthRoutes;
import com.guzuro.Routes.CompanyRoutes;
import com.guzuro.Routes.Config.SaleRoutes;
import com.guzuro.Routes.UserRoutes;
import com.guzuro.Routes.WarehouseRoutes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);


        router.route()
                .handler(
                        CorsHandler.create()
//                                .allowedMethod(HttpMethod.GET)
//                                .allowedMethod(HttpMethod.POST)
//                                .allowedMethod(HttpMethod.PUT)
//                                .allowedMethod(HttpMethod.OPTIONS)
                                .allowCredentials(true)
                                .allowedHeader("Access-Control-Allow-Headers")
                                .allowedHeader("Access-Control-Allow-Method")
                                .allowedHeader("Access-Control-Allow-Origin")
                                .allowedHeader("Access-Control-Allow-Credentials")
                                .allowedHeader("Content-Type")
                )
                .handler(
                        BodyHandler
                                .create()
                                .setMergeFormAttributes(true)
                                .setUploadsDirectory("webroot")
                ).handler(
                SessionHandler.
                        create(LocalSessionStore.create(vertx))
                        .setCookieSameSite(CookieSameSite.NONE)
                        .setCookieHttpOnlyFlag(true)
                        .setCookieless(false)
                        .setCookieSecureFlag(true));

        router.route("/api");
        router.mountSubRouter("/auth", new AuthRoutes(vertx).setRoutes(vertx));
        router.mountSubRouter("/user", new UserRoutes(vertx).setRoutes());

        router.mountSubRouter("/company", new CompanyRoutes(vertx).setRoutes());
        router.mountSubRouter("/warehouse", new WarehouseRoutes(vertx).setRoutes());


        router.mountSubRouter("/config/sales", new SaleRoutes(vertx).setRoutes(vertx));


//        router.mountSubRouter("/products", ProductRoutes.setRoutes(vertx));


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
