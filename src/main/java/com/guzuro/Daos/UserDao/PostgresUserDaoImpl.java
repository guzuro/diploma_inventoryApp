package com.guzuro.Daos.UserDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

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
    public CompletableFuture<User> updateUser(User user) {
        CompletableFuture<User> fut = new CompletableFuture<>();

        this.pgClient.preparedQuery("UPDATE db_user " +
                "SET email = $1, first_name= $2, last_name= $3, phone= $4, role= $5 " +
                "WHERE id = $6" +
                "RETURNING id, email, first_name, last_name, phone, company, role")
                .execute(Tuple.of(
                        user.getEmail(),
                        user.getFirst_name(),
                        user.getLast_name(),
                        user.getPhone(),
                        user.getRole(),
                        user.getId()
                ), ar -> {
                    if (ar.succeeded()) {
                        User updatedUser = ar.result().iterator().next().toJson().mapTo(User.class);
                        fut.complete(updatedUser);
                    } else {
                        fut.completeExceptionally(ar.cause());
                    }
                });
        return fut;
    }

    @Override
    public CompletableFuture<Boolean> deleteUser(int userId) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> verifyUserPassword(User user) {
        CompletableFuture<Boolean> isTheSame = new CompletableFuture<>();

        this.pgClient.preparedQuery("SELECT password FROM db_user WHERE id = $1;")
                .execute(Tuple.of(user.getId()), ar -> {
                    if (ar.succeeded()) {
                        String originalPassword = ar.result().iterator().next().getString("password");
                        isTheSame.complete(originalPassword.equals(user.getPassword()));
                    } else {
                        isTheSame.completeExceptionally(ar.cause());
                    }
                });
        return isTheSame;
    }
}
