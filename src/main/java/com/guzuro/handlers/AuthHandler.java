package com.guzuro.handlers;

import com.guzuro.Daos.AuthDao.AuthDao;
import com.guzuro.Daos.AuthDao.LoginData;
import com.guzuro.Daos.AuthDao.PostgresAuthDaoImpl;
import com.guzuro.Daos.CompanyDao.Company;
import com.guzuro.Daos.CompanyDao.CompanyDao;
import com.guzuro.Daos.CompanyDao.PostgresCompanyDaoImpl;
import com.guzuro.Daos.UserDao.User;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

public class AuthHandler {

    AuthDao authDao;
    CompanyDao companyDao;

    public AuthHandler(Vertx vertx) {
        this.authDao = new PostgresAuthDaoImpl(vertx);
        this.companyDao = new PostgresCompanyDaoImpl(vertx);
    }

    public void register(RoutingContext context) {
        HttpServerResponse response = context.response();
        User reqUser = context.getBodyAsJson().mapTo(User.class);
        reqUser.setRole("Administrator");
        this.authDao.register(reqUser)
                .thenAccept(user -> {
                    Company company = new Company(user.getCompany());

                    this.companyDao.createCompany(company)
                            .thenAccept(resCompany -> {
                                Session session = context.session();
                                session.put("id", user.getId());

                                response.putHeader("content-type", "application/json; charset=UTF-8")
                                        .setStatusCode(201)
                                        .end(JsonObject.mapFrom(user).encodePrettily());

                            })
                            .exceptionally(throwable -> {
                                response.setStatusCode(500)
                                        .putHeader("content-type", "application/json; charset=UTF-8")
                                        .end(throwable.getCause().getMessage());
                                return null;
                            });
                }).exceptionally(throwable -> {
            response.setStatusCode(500)
                    .putHeader("content-type", "application/json; charset=UTF-8")
                    .end(throwable.getCause().getMessage());
            return null;
        });
    }

    public void login(RoutingContext context) {

        HttpServerResponse response = context.response();
        LoginData loginData = context.getBodyAsJson().mapTo(LoginData.class);

        this.authDao.login(loginData).thenAccept(resUser -> {

            Session session = context.session();
            if (session.get("id") == null) {
                session.put("id", resUser.getId());
            }

            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resUser).encodePrettily());

        }).exceptionally(throwable -> {
            if (throwable.getMessage().contains("NOT FOUND")) {
                response.setStatusCode(404).end();
            } else {
                response.setStatusCode(500).end(throwable.getMessage());
            }
            return null;
        });
    }

    public void isAuth(RoutingContext context) {
        HttpServerResponse response = context.response();
        Session session = context.session();
        if (session.get("id") == null) {
            response.setStatusCode(401).end();
        } else {
            this.authDao.getUserById(session.get("id")).thenAccept(resUser -> {
                response.putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusCode(200)
                        .end(JsonObject.mapFrom(resUser).encodePrettily());

            }).exceptionally(throwable -> {
                if (throwable.getMessage().contains("NOT FOUND")) {
                    response.setStatusCode(404).end();
                } else {
                    response.setStatusCode(500).end(throwable.getMessage());
                }
                return null;
            });
        }
    }

    public void logout(RoutingContext context) {
        HttpServerResponse response = context.response();
        Session session = context.session();
        session.destroy();
        response.setStatusCode(200).end();
    }
}
