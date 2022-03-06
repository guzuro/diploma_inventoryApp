package com.guzuro.Daos.AuthDao;

import com.guzuro.Daos.UserDao.User;

import java.util.concurrent.CompletableFuture;

public interface AuthDao {

    CompletableFuture<User> register(User user);

    CompletableFuture<User> login(LoginData data);

    CompletableFuture<User> getUserById(Number id);
}
