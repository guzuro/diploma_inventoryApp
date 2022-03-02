package com.guzuro.Daos.DaoFactory;

import com.guzuro.Daos.AuthDao.AuthDao;
import com.guzuro.Daos.AuthDao.PostgresAuthDaoImpl;
import com.guzuro.Daos.ProductDao.PostgresProductDaoImpl;
import com.guzuro.Daos.ProductDao.ProductDao;
import com.guzuro.Daos.UserDao.PostgresUserDaoImpl;
import com.guzuro.Daos.UserDao.UserDao;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public class PostgresDAOFactory extends DaoFactory {

    public static SqlClient createConnection(Vertx vertx) {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("5432")
                .setDatabase("inventory_app")
                .setUser("postgres")
                .setPassword("Ffanko159357458256s");

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        return PgPool.client(vertx, connectOptions, poolOptions);
    }

    public ProductDao getProductDao(Vertx vertx) {
        return new PostgresProductDaoImpl(vertx);
    }
    public AuthDao getAuthDao(Vertx vertx) {
        return new PostgresAuthDaoImpl(vertx);
    }
    public UserDao getUserDao(Vertx vertx) {
        return new PostgresUserDaoImpl(vertx);
    }

}

