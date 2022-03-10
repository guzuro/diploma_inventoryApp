package com.guzuro.Daos.UserDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Dto.UserCompanyDto;
import com.guzuro.Models.Roles.Employee;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresUserDaoImpl implements UserDao {
    SqlClient pgClient;

    public PostgresUserDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<User> addUser(UserCompanyDto userCompanyDto) {
        CompletableFuture<User> future = new CompletableFuture<>();
        pgClient.preparedQuery("INSERT INTO db_user(email, password, first_name, last_name, phone, role, company_id) " +
                "VALUES ($1, $2, $3, $4, $5, $6, $7) " +
                "RETURNING id, email, first_name, last_name, phone, role")
                .execute(Tuple.of(
                        userCompanyDto.user.getEmail(),
                        userCompanyDto.user.getPassword(),
                        userCompanyDto.user.getFirst_name(),
                        userCompanyDto.user.getLast_name(),
                        userCompanyDto.user.getPhone(),
                        "salesman",
                        userCompanyDto.companyId
                ), ar -> {
                    if (ar.succeeded()) {
                        User dbEmployee = ar.result().iterator().next().toJson().mapTo(User.class);
                        future.complete(dbEmployee);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<User> changeUserRole(int userId, String role) {
        return null;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<Employee>> getUsers(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<Employee>> fut = new CompletableFuture<>();
        pgClient.preparedQuery(
                "SELECT id, first_name, last_name, role FROM db_user WHERE role <> $1 AND company_id = $2")
                .execute(Tuple.of(
                        "Administrator",
                        company_id
                ), ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<Employee> usersList = new CopyOnWriteArrayList<>();
                        ar.result().forEach(row -> {
                            Employee user = row.toJson().mapTo(Employee.class);
                            usersList.add(user);
                        });
                        fut.complete(usersList);
                    } else {
                        fut.completeExceptionally(ar.cause());
                    }
                });
        return fut;
    }

    @Override
    public CompletableFuture<User> getUserInfo(User user) {
        return null;
    }

    @Override
    public CompletableFuture<Employement> setUserEmployement(Employement employement) {
        CompletableFuture<Employement> fut = new CompletableFuture<>();
        pgClient.preparedQuery(
                "INSERT INTO db_employment(user_id, employement_date, salary) " +
                        "VALUES ($1, $2, $3) RETURNING id, user_id, employement_date, salary")
                .execute(Tuple.of(
                        employement.getUser_id(),
                        employement.getEmployement_date(),
                        employement.getSalary()
                ), ar -> {
                    if (ar.succeeded()) {
                        Employement dbEmployement = ar.result().iterator().next().toJson().mapTo(Employement.class);
                        fut.complete(dbEmployement);
                    } else {
                        fut.completeExceptionally(ar.cause());
                    }
                });
        return fut;
    }

    @Override
    public CompletableFuture<Employement> getUserEmployement(int user_id) {
        CompletableFuture<Employement> fut = new CompletableFuture<>();
        pgClient.preparedQuery(
                "SELECT id, user_id, employement_date, salary FROM db_employment WHERE user_id = $1")
                .execute(Tuple.of(
                        user_id
                ), ar -> {
                    if (ar.succeeded()) {
                        if (ar.result().rowCount() > 0) {
                            Employement employement = ar.result().iterator().next().toJson().mapTo(Employement.class);
                            fut.complete(employement);
                        } else {
                            Employement empl = new Employement();
                            fut.complete(empl);
                        }
                    } else {
                        fut.completeExceptionally(ar.cause());
                    }
                });
        return fut;
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
