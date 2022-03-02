package com.guzuro.Daos.AuthDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.UserDao.User;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;

import java.util.concurrent.CompletableFuture;

public class PostgresAuthDaoImpl implements AuthDao {
    SqlClient pgClient;

    public PostgresAuthDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<User> register(User user) {
        CompletableFuture<User> newUser = new CompletableFuture<>();

        return null;
    }

    @Override
    public CompletableFuture<User> login(LoginData data) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> isAuth() {
        return null;
    }
}
