package com.guzuro;

import com.guzuro.Routes.*;
import com.guzuro.Routes.Config.CategoryRoutes;
import com.guzuro.Routes.Config.EmployeeRolesRoutes;
import com.guzuro.Routes.Config.SaleRoutes;
import com.guzuro.Routes.Config.WarehouseRoutes;
import com.guzuro.handlers.UploadFileHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.impl.URIDecoder;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("origin");

        allowedHeaders.add("Access-Control-Allow-Headers");
        allowedHeaders.add("Access-Control-Allow-Method");
        allowedHeaders.add("Access-Control-Allow-Credentials");
        allowedHeaders.add("Content-Type");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);


        router
                .route()
                .handler(
                        CorsHandler.create("*")
                                .allowCredentials(true)
                                .allowedHeaders(allowedHeaders)
                                .allowedMethods(allowedMethods)
                );

        router.route()
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

        router.route("/assets/*").handler(StaticHandler.create("webroot")).handler(handler -> {
            try {
                String path = handler.request().path();

                String result = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8.name());
                String[] pathSplited = result.split("/");
                String fileName = pathSplited[3];

                HttpServerResponse response = handler.response();
                response.putHeader("Transfer-Encoding", "chunked")
                        .sendFile("webroot/" + fileName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        });

        router.route("/api");
        router.mountSubRouter("/auth", new AuthRoutes(vertx).setRoutes(vertx));
        router.mountSubRouter("/user", new UserRoutes(vertx).setRoutes());

        router.mountSubRouter("/company", new CompanyRoutes(vertx).setRoutes());

        router.mountSubRouter("/warehouse", new WarehouseRoutes(vertx).setRoutes());
        router.mountSubRouter("/config/sales", new SaleRoutes(vertx).setRoutes(vertx));
        router.mountSubRouter("/config/category", new CategoryRoutes(vertx).setRoutes(vertx));
        router.mountSubRouter("/config/employeeroles", new EmployeeRolesRoutes(vertx).setRoutes(vertx));

        router.post("/uploadfile").handler(UploadFileHandler::uploadFile);

        router.mountSubRouter("/products", new ProductRoutes(vertx).setRoutes(vertx));

        router.mountSubRouter("/suppliers", new SupplierRoutes(vertx).setRoutes(vertx));

        router.mountSubRouter("/incomedoc", new IncomeDocRoutes(vertx).setRoutes(vertx));

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
