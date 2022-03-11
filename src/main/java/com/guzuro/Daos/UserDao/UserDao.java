package com.guzuro.Daos.UserDao;

import com.guzuro.Dto.UserCompanyDto;
import com.guzuro.Models.Roles.Employee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public interface UserDao {
    CompletableFuture<CopyOnWriteArrayList<Employee>> getUsers(int company_id);

    CompletableFuture<User> addEmployee(UserCompanyDto user);

    CompletableFuture<User> updateEmployee(Employee employee);

    CompletableFuture<User> updateUser(User user);

    CompletableFuture<Boolean> deleteUser(int userId);

    CompletableFuture<Boolean> verifyUserPassword(User user);

    CompletableFuture<Employement> getUserEmployement(int userId);

    CompletableFuture<Employement> setUserEmployement(Employement employement);

    CompletableFuture<Employement> updateEmployement(Employement employement);

}
