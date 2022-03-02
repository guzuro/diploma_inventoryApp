package com.guzuro.Daos.UserDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;

import java.util.concurrent.CompletableFuture;

public class PostgresUserDaoImpl implements UserDao {
    SqlClient pgClient;

    public PostgresUserDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<User> addUser(User user) {
        return null;
    }

    @Override
    public CompletableFuture<User> changeUserRole(int userId, String role) {
        return null;
    }

    @Override
    public CompletableFuture<User> getUserInfo(User user) {
        return null;
    }

    @Override
    public CompletableFuture<User> updateUser(User todo) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteUser(int userId) {
        return null;
    }
}
