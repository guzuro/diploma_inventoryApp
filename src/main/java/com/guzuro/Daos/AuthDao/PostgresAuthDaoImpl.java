package com.guzuro.Daos.AuthDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.UserDao.User;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;

public class PostgresAuthDaoImpl implements AuthDao {
    SqlClient pgClient;

    public PostgresAuthDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<User> register(User user) {
        CompletableFuture<User> future = new CompletableFuture<>();
        pgClient.preparedQuery(
                "INSERT INTO db_user(email, password, first_name, last_name, phone, company, role) " +
                        "VALUES ($1, $2, $3, $4, $5, $6, $7) " +
                        "RETURNING id, email, first_name, last_name, phone, company, role")
                .execute(Tuple.of(
                        user.getEmail(),
                        user.getPassword(),
                        user.getFirst_name(),
                        user.getLast_name(),
                        user.getPhone(),
                        user.getCompany(),
                        user.getRole()
                ), ar -> {
                    if (ar.succeeded()) {
                        User newUser = ar.result().iterator().next().toJson().mapTo(User.class);
                        future.complete(newUser);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<User> login(LoginData data) {
        CompletableFuture<User> future = new CompletableFuture<>();
        pgClient.preparedQuery(
                "SELECT id, email, first_name, last_name, phone, company, role FROM db_user WHERE email=$1 AND password = $2")
                .execute(Tuple.of(
                        data.getEmail(),
                        data.getPassword()
                ), ar -> {
                    if (ar.succeeded()) {
                        if (ar.result().rowCount() == 1) {
                            User user = ar.result().iterator().next().toJson().mapTo(User.class);
                            future.complete(user);
                        } else {
                            future.completeExceptionally(new Throwable("NOT FOUND"));
                        }
                    } else {
                        System.out.println(ar.cause().getMessage());
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;

    }
}
