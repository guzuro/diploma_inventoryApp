package com.guzuro.Daos.AuthDao;

import com.guzuro.Daos.UserDao.User;
import com.guzuro.Dto.UserCompanyDto;

import java.util.concurrent.CompletableFuture;

public interface AuthDao {
    CompletableFuture<User> register(UserCompanyDto userCompanyDto);

    CompletableFuture<UserCompanyDto> login(LoginData data);

    CompletableFuture<UserCompanyDto> getUserById(Number id);
}

