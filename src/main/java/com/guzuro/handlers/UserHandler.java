package com.guzuro.handlers;

import com.guzuro.Daos.AuthDao.AuthDao;
import com.guzuro.Daos.AuthDao.PostgresAuthDaoImpl;
import com.guzuro.Daos.UserDao.PostgresUserDaoImpl;
import com.guzuro.Daos.UserDao.User;
import com.guzuro.Daos.UserDao.UserDao;
import com.guzuro.Models.Roles.Administrator;
import com.guzuro.Models.Roles.Employee;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserHandler {

    UserDao dao;

    public UserHandler(Vertx vertx) {
        this.dao = new PostgresUserDaoImpl(vertx);
    }

    public void getUserInfo(RoutingContext context) {
        System.out.println(context);
    }

    public void addUser(RoutingContext context) {
        System.out.println(context);
    }

    public void changeUserRole(RoutingContext context) {
        System.out.println(context);
    }

    public void updateUser(RoutingContext context) {
        HttpServerResponse response = context.response();
        JsonObject reqData = context.getBodyAsJson();

        User user = reqData.mapTo(User.class);
        this.dao.verifyUserPassword(user).thenAccept(password -> {
            if (password) {
                this.dao.updateUser(user).thenAccept(resUser -> {
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
            } else {
                response.setStatusCode(400)
                        .setStatusMessage("Wrong original password")
                        .end();

            }
        }).exceptionally(throwable -> {
            response.setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void deleteUser(RoutingContext context) {
        System.out.println(context);
    }
}
