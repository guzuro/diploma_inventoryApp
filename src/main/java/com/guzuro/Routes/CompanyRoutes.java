package com.guzuro.Routes;

import com.guzuro.handlers.AuthHandler;
import com.guzuro.handlers.CompanyHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class CompanyRoutes {

    CompanyHandler companyHandler;

    Vertx vertx;

    public CompanyRoutes(Vertx vertx) {
        this.companyHandler = new CompanyHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes() {
        Router router = Router.router(this.vertx);

        router.post("/update").handler(rc -> this.companyHandler.updateCompanyInfo(rc));

        return router;
    }
}
