

package com.guzuro.Daos.AuthDao;

import com.guzuro.Daos.DaoFactory.PostgresDAOFactory;
import com.guzuro.Daos.UserDao.User;
import com.guzuro.Models.Roles.Administrator;
import com.guzuro.Models.Roles.Employee;
import com.guzuro.Dto.UserCompanyDto;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;

import java.util.concurrent.CompletableFuture;

public class PostgresAuthDaoImpl implements AuthDao {
    SqlClient pgClient;

    public PostgresAuthDaoImpl(Vertx vertx) {
        pgClient = PostgresDAOFactory.createConnection(vertx);
    }

    @Override
    public CompletableFuture<User> register(UserCompanyDto userCompanyDto) {
        CompletableFuture<User> future = new CompletableFuture<>();
        pgClient.preparedQuery("INSERT INTO db_user(email, password, first_name, last_name, phone, role, company_id) " + "VALUES ($1, $2, $3, $4, $5, $6, $7) " + "RETURNING id, email, first_name, last_name, phone, role").execute(Tuple.of(userCompanyDto.user.getEmail(), userCompanyDto.user.getPassword(), userCompanyDto.user.getFirst_name(), userCompanyDto.user.getLast_name(), userCompanyDto.user.getPhone(), userCompanyDto.user.getRole(), userCompanyDto.companyId), ar -> {
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
    public CompletableFuture<UserCompanyDto> login(LoginData data) {
        CompletableFuture<UserCompanyDto> future = new CompletableFuture<>();
        pgClient.preparedQuery("SELECT id, email, first_name, last_name, phone, role, company_id FROM db_user WHERE email=$1 AND password = $2")
                .execute(Tuple.of(data.getEmail(), data.getPassword()),
                        ar -> {
                            if (ar.succeeded()) {
                                if (ar.result().rowCount() > 0) {
                                    UserCompanyDto userCompanyDto = new UserCompanyDto();
                                    User user = new User();

                                    JsonObject jsonResult = ar.result().iterator().next().toJson();

                                    user.setId(jsonResult.getInteger("id"));
                                    user.setEmail(jsonResult.getString("email"));
                                    user.setRole(jsonResult.getString("role"));
                                    user.setFirst_name(jsonResult.getString("first_name"));
                                    user.setLast_name(jsonResult.getString("last_name"));
                                    user.setPhone(jsonResult.getString("phone"));

                                    userCompanyDto.companyId = jsonResult.getNumber("company_id");
                                    userCompanyDto.user = user;
                                    future.complete(userCompanyDto);
                                } else {
                                    future.completeExceptionally(new Throwable("NOT FOUND"));
                                }
                            } else {
                                future.completeExceptionally(ar.cause());
                            }
                        });
        return future;
    }

    //
    //
    @Override
    public CompletableFuture<UserCompanyDto> getUserById(Number id) {
        CompletableFuture<UserCompanyDto> future = new CompletableFuture<>();
        pgClient.preparedQuery("SELECT id, email, first_name, last_name, phone, role, company_id " +
                "FROM db_user " +
                "WHERE id=$1")
                .execute(Tuple.of(id), ar -> {
                    if (ar.succeeded()) {
                        if (ar.result().rowCount() > 0) {
                            UserCompanyDto userCompanyDto = new UserCompanyDto();
                            User user = new User();

                            JsonObject jsonResult = ar.result().iterator().next().toJson();

                            user.setId(jsonResult.getInteger("id"));
                            user.setEmail(jsonResult.getString("email"));
                            user.setRole(jsonResult.getString("role"));
                            user.setFirst_name(jsonResult.getString("first_name"));
                            user.setLast_name(jsonResult.getString("last_name"));
                            user.setPhone(jsonResult.getString("phone"));

                            userCompanyDto.companyId = jsonResult.getNumber("company_id");
                            userCompanyDto.user = user;
                            future.complete(userCompanyDto);
                        } else {
                            future.completeExceptionally(new Throwable("NOT FOUND"));
                        }
                    } else {
                        future.completeExceptionally(ar.cause());
                    }
                });
        return future;
    }
}





