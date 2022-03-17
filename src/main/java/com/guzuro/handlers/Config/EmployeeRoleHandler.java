package com.guzuro.handlers.Config;

import com.guzuro.Daos.Config.EmployeeRole.EmployeeRole;
import com.guzuro.Daos.Config.EmployeeRole.EmployeeRoleDao;
import com.guzuro.Daos.Config.EmployeeRole.PostgresEmployeeRoleDaoImpl;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class EmployeeRoleHandler {
    EmployeeRoleDao employeeRoleDao;

    public EmployeeRoleHandler(Vertx vertx) {
        this.employeeRoleDao = new PostgresEmployeeRoleDaoImpl(vertx);
    }

    public void add(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        EmployeeRole employeeRole = routingContext.getBodyAsJson().getJsonObject("employee_role").mapTo(EmployeeRole.class);
        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.employeeRoleDao.addEmployeeRole(employeeRole, company_id).thenAccept(resEmployeeRole -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resEmployeeRole).encodePrettily());
        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void get(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int company_id = routingContext.getBodyAsJson().getInteger("company_id");

        this.employeeRoleDao.getEmployeeRoles(company_id).thenAccept(employeeRoles -> {
            JsonArray employeeRolesJson = new JsonArray();
            if (employeeRoles.size() > 0)
                employeeRoles.forEach(warehouse -> employeeRolesJson.add(JsonObject.mapFrom(warehouse)));

            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(employeeRolesJson.encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void update(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        EmployeeRole employeeRole = routingContext.getBodyAsJson().getJsonObject("employee_role").mapTo(EmployeeRole.class);

        this.employeeRoleDao.updateEmployeeRole(employeeRole).thenAccept(resEmployeeRole -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(200)
                    .end(JsonObject.mapFrom(resEmployeeRole).encodePrettily());

        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }

    public void delete(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();

        int role_id = routingContext.getBodyAsJson().getInteger("role_id");

        this.employeeRoleDao.deleteEmployeeRole(role_id).thenAccept(aBoolean -> {
            if (aBoolean) {
                response.putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusCode(200)
                        .end();
            } else {
                response.putHeader("content-type", "application/json; charset=UTF-8")
                        .setStatusCode(404)
                        .end();
            }
        }).exceptionally(throwable -> {
            response.putHeader("content-type", "application/json; charset=UTF-8")
                    .setStatusCode(500)
                    .end(throwable.getMessage());
            return null;
        });
    }
}
