package com.guzuro.Routes;

import com.guzuro.handlers.CompanyHandler;
import com.guzuro.handlers.StatisticsHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class StatisticsRoutes {
    StatisticsHandler statisticsHandler;

    Vertx vertx;

    public StatisticsRoutes(Vertx vertx) {
        this.statisticsHandler = new StatisticsHandler(vertx);
        this.vertx = vertx;
    }

    public Router setRoutes() {
        Router router = Router.router(this.vertx);

        router.post("/getstatistics").handler(rc -> this.statisticsHandler.getStatistics(rc));

        return router;
    }
}
