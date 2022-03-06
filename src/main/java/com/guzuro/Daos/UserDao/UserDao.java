package com.guzuro.Daos.UserDao;

import java.util.concurrent.CompletableFuture;

public interface UserDao {

    CompletableFuture<User> getUserInfo(User user);

    CompletableFuture<User>addUser(User user);

    CompletableFuture<User> changeUserRole(int userId, String role);

    CompletableFuture<User> updateUser(User user);

    CompletableFuture<Boolean> deleteUser(int userId);

    CompletableFuture<Boolean> verifyUserPassword(User user);

}
