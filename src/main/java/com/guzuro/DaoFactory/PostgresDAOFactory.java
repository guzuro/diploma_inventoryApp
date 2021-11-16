package com.guzuro.DaoFactory;

import com.guzuro.ProductDao.PostgresProductDaoImpl;
import com.guzuro.ProductDao.ProductDao;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public class PostgresDAOFactory extends DaoFactory {

    public static SqlClient createConnection(Vertx vertx) {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("*")
                .setDatabase("*")
                .setUser("*")
                .setPassword("*");

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(5);

        return PgPool.client(vertx, connectOptions, poolOptions);
    }

    public ProductDao getProductDao(Vertx vertx) {
        return new PostgresProductDaoImpl(vertx);
    }

}

