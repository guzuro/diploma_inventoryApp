package com.guzuro.handlers;

import com.guzuro.Daos.AuthDao.AuthDao;
import com.guzuro.Daos.AuthDao.PostgresAuthDaoImpl;
import com.guzuro.Daos.CompanyDao.CompanyDao;
import com.guzuro.Daos.CompanyDao.PostgresCompanyDaoImpl;
import com.guzuro.Daos.UserDao.Employement;
import com.guzuro.Daos.UserDao.PostgresUserDaoImpl;
import com.guzuro.Daos.UserDao.User;
import com.guzuro.Daos.UserDao.UserDao;
import com.guzuro.Dto.UserCompanyDto;
import com.guzuro.Models.Roles.Administrator;
import com.guzuro.Models.Roles.Employee;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserHandler {

    UserDao dao;
    CompanyDao companyDao;

    public UserHandler(Vertx vertx) {
        this.dao = new PostgresUserDaoImpl(vertx);
        this.companyDao = new PostgresCompanyDaoImpl(vertx);
    }

    public void getAllEmployes(RoutingContext context) {
        HttpServerResponse response = context.response();

        this.dao.getUsers(context.getBodyAsJson().getInteger("company_id"))
                .thenAccept(employees -> {
                    JsonArray employesJson = new JsonArray();
                    employees.forEach(employee -> employesJson.add(JsonObject.mapFrom(employee)));
                    context.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=UTF-8")
                            .end(employesJson.encodePrettily());

                }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void addEmployee(RoutingContext context) {
        HttpServerResponse response = context.response();

        UserCompanyDto userCompanyDto = new UserCompanyDto();
        userCompanyDto.user = context.getBodyAsJson().getJsonObject("user").mapTo(User.class);
        userCompanyDto.companyId = context.getBodyAsJson().getInteger("company_id");

        Employement employement = context.getBodyAsJson().getJsonObject("employment").mapTo(Employement.class);

        this.dao.addEmployee(userCompanyDto).thenAccept(resUser -> {
            employement.setUser_id(resUser.getId());

            this.dao.setUserEmployement(employement).thenAccept(resEmployement -> {

                Employee employee = new Employee();

                employee.setEmployement(resEmployement);
                employee.setEmail(resUser.getEmail());
                employee.setLast_name(resUser.getLast_name());
                employee.setFirst_name(resUser.getFirst_name());
                employee.setRole(resUser.getRole());
                employee.setId(resUser.getId());
                employee.setPhone(resUser.getPhone());

                response.putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusCode(200)
                        .end(JsonObject.mapFrom(employee).encodePrettily());

            }).exceptionally(throwable -> {
                response.putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusCode(500)
                        .end(throwable.getMessage());
                return null;
            });
        }).exceptionally(throwable -> {
            response.setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void updateEmployee(RoutingContext context) {
        HttpServerResponse response = context.response();
        JsonObject reqData = context.getBodyAsJson();

        User user = reqData.getJsonObject("user").mapTo(User.class);

        Employement employement = reqData.getJsonObject("employement").mapTo(Employement.class);
        employement.setUser_id(user.getId());

        this.dao.updateUser(user).thenAccept(resUser -> {
            if (reqData.getJsonObject("employement").getInteger("id") == null) {
                this.dao.setUserEmployement(employement)
                        .thenAccept(resEmployment -> this.handleEmployeeUpdate(resEmployment, resUser, response))
                        .exceptionally(throwable -> {
                            response.putHeader("content-type", "application/json; charset=UTF-8")
                                    .setStatusCode(500).end(throwable.getMessage());
                            return null;
                        });
            } else {
                this.dao.updateEmployement(employement)
                        .thenAccept(resEmployment -> this.handleEmployeeUpdate(resEmployment, resUser, response))
                        .exceptionally(throwable -> {
                            response.putHeader("content-type", "application/json; charset=UTF-8")
                                    .setStatusCode(500).end(throwable.getMessage());
                            return null;
                        });
            }
        }).exceptionally(throwable -> {
            if (throwable.getMessage().contains("NOT FOUND")) {
                response.putHeader("content-type", "application/json; charset=UTF-8").setStatusCode(404).end();
            } else {
                response.putHeader("content-type", "application/json; charset=UTF-8").setStatusCode(500).end(throwable.getMessage());
            }
            return null;
        });
    }

    public void updateUser(RoutingContext context) {
        HttpServerResponse response = context.response();
        JsonObject reqData = context.getBodyAsJson();

        User user = reqData.mapTo(User.class);
        this.dao.verifyUserPassword(user).thenAccept(password -> {
            if (password) {
                this.dao.updateUser(user).thenAccept(resUser -> {
                    response.putHeader("content-type", "application/json; charset=UTF-8")
                            .setStatusCode(200)
                            .end(JsonObject.mapFrom(resUser).encodePrettily());

                }).exceptionally(throwable -> {
                    if (throwable.getMessage().contains("NOT FOUND")) {
                        response.setStatusCode(404).end();
                    } else {
                        response.setStatusCode(500).end(throwable.getMessage());
                    }
                    return null;
                });
            } else {
                response.setStatusCode(400)
                        .setStatusMessage("Wrong original password")
                        .end();

            }
        }).exceptionally(throwable -> {
            response.setStatusCode(500).end(throwable.getMessage());
            return null;
        });
    }

    public void deleteUser(RoutingContext context) {
        System.out.println(context);
    }


    public void handleEmployeeUpdate(Employement employment, User user, HttpServerResponse response) {
        Employee employee = new Employee();
        employee.setEmployement(employment);
        employee.setEmail(user.getEmail());
        employee.setLast_name(user.getLast_name());
        employee.setFirst_name(user.getFirst_name());
        employee.setRole(user.getRole());
        employee.setId(user.getId());
        employee.setPhone(user.getPhone());

        employee.setEmployement(employment);

        response.putHeader("content-type", "application/json; charset=UTF-8")
                .setStatusCode(200)
                .end(JsonObject.mapFrom(employee).encodePrettily());
    }
}
