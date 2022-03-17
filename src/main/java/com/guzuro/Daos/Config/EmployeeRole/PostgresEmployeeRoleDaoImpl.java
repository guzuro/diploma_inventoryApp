package com.guzuro.Daos.Config.EmployeeRole;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresEmployeeRoleDaoImpl implements EmployeeRoleDao {
    SqlClient pgClient;

    public PostgresEmployeeRoleDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<EmployeeRole> addEmployeeRole(EmployeeRole employeeRole, int company_id) {
        CompletableFuture<EmployeeRole> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("INSERT INTO db_employee_roles(name, company_id)" +
                "VALUES ($1, $2) RETURNING id, name;").execute(
                Tuple.of(employeeRole.getName(), company_id),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(EmployeeRole.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                }
        );
        return future;
    }

    @Override
    public CompletableFuture<EmployeeRole> updateEmployeeRole(EmployeeRole employeeRole) {
        CompletableFuture<EmployeeRole> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("UPDATE db_employee_roles " +
                "SET name = $1 WHERE id = $2 " +
                "RETURNING id, name").execute(
                Tuple.of(employeeRole.getName(), employeeRole.getId()),
                ar -> {
                    if (ar.succeeded()) {
                        future.complete(ar.result().iterator().next().toJson().mapTo(EmployeeRole.class));
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                }
        );
        return future;
    }

    @Override
    public CompletableFuture<Boolean> deleteEmployeeRole(int role_id) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("DELETE FROM db_employee_roles WHERE id = $1;")
                .execute(Tuple.of(role_id), ar -> {
                    if (ar.succeeded()) {
                        future.complete(true);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<CopyOnWriteArrayList<EmployeeRole>> getEmployeeRoles(int company_id) {
        CompletableFuture<CopyOnWriteArrayList<EmployeeRole>> future = new CompletableFuture<>();

        this.pgClient.preparedQuery("SELECT id, name FROM db_employee_roles WHERE company_id = $1;")
                .execute(Tuple.of(company_id), ar -> {
                    if (ar.succeeded()) {
                        CopyOnWriteArrayList<EmployeeRole> employeeRoles = new CopyOnWriteArrayList<>();

                        if (ar.result().rowCount() > 0) {
                            ar.result().forEach(row -> employeeRoles.add(row.toJson().mapTo(EmployeeRole.class)));
                        }

                        future.complete(employeeRoles);
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }
}



