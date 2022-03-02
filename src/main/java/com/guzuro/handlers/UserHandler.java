package com.guzuro.handlers;

import com.guzuro.Daos.AuthDao.AuthDao;
import com.guzuro.Daos.AuthDao.PostgresAuthDaoImpl;
import com.guzuro.Daos.UserDao.PostgresUserDaoImpl;
import com.guzuro.Daos.UserDao.UserDao;
import io.vertx.core.Vertx;
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
        System.out.println(context);
    }

    public void deleteUser(RoutingContext context) {
        System.out.println(context);
    }
}
